/*******************************************************************************
 *
 *	Copyright (c) 2022 Nick Battle.
 *
 *	Author: Nick Battle
 *
 *	This file is part of VDMJ.
 *
 *	VDMJ is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	VDMJ is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with VDMJ.  If not, see <http://www.gnu.org/licenses/>.
 *	SPDX-License-Identifier: GPL-3.0-or-later
 *
 ******************************************************************************/

package workspace;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import com.fujitsu.vdmj.Settings;
import com.fujitsu.vdmj.lex.Dialect;
import com.fujitsu.vdmj.messages.RTLogger;

import dap.DAPMessageList;
import dap.DAPRequest;
import json.JSONObject;
import lsp.LSPException;
import lsp.Utils;
import rpc.RPCErrors;
import workspace.plugins.CTPlugin;

public class DAPXWorkspaceManager
{
	private static DAPXWorkspaceManager INSTANCE = null;
	private final PluginRegistry registry;
	private final DAPWorkspaceManager dapManager;
	
	protected DAPXWorkspaceManager()
	{
		this.registry = PluginRegistry.getInstance();
		this.dapManager = DAPWorkspaceManager.getInstance();
	}

	public static synchronized DAPXWorkspaceManager getInstance()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new DAPXWorkspaceManager();		
			Diag.info("Created DAPXWorkspaceManager");
		}

		return INSTANCE;
	}

	/**
	 * This is only used by unit testing.
	 */
	public static void reset()
	{
		if (INSTANCE != null)
		{
			INSTANCE = null;
		}
	}
	
	/**
	 * DAPX extensions...
	 */
	public JSONObject ctRunOneTrace(DAPRequest request, String name, long testNumber) throws LSPException
	{
		CTPlugin ct = registry.getPlugin("CT");
		
		if (ct.isRunning())
		{
			Diag.error("Previous trace is still running...");
			throw new LSPException(RPCErrors.InvalidRequest, "Trace still running");
		}

		/**
		 * If the specification has been modified since we last ran (or nothing has yet run),
		 * we have to re-create the interpreter, otherwise the old interpreter (with the old tree)
		 * is used to "generate" the trace names, so changes are not picked up. Note that a
		 * new tree will have no breakpoints, so if you had any set via a launch, they will be
		 * ignored.
		 */
		dapManager.refreshInterpreter();
		
		if (dapManager.specHasErrors())
		{
			throw new LSPException(RPCErrors.ContentModified, "Specification has errors");
		}
		
		dapManager.setNoDebug(false);	// Force debug on for runOneTrace

		return ct.runOneTrace(Utils.stringToName(name), testNumber);
	}

	public DAPMessageList rtLog(DAPRequest request, String logfile)
	{
		String message = null;
		
		if (Settings.dialect != Dialect.VDM_RT)
		{
			message = "Command only available for VDM-RT";
			Diag.error(message);
			return new DAPMessageList(request, false, message, null);			
		}

		if (logfile == null)
		{
			RTLogger.enable(false);
			message = "RT event logging disabled";
		}
		else
		{
			try
			{
				File file = new File(logfile);
				PrintWriter p = new PrintWriter(new FileOutputStream(logfile, false));
				RTLogger.setLogfile(p);
				message = "RT events now logged to " + file.getAbsolutePath();
			}
			catch (FileNotFoundException e)
			{
				message = "Cannot create RT event log: " + e.getMessage();
				Diag.error(message);
				return new DAPMessageList(request, false, message, null);			
			}
		}
		
		Diag.info(message);
		return new DAPMessageList(request, new JSONObject("result", message));
	}
}
