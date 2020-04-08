/*******************************************************************************
 *
 *	Copyright (c) 2020 Nick Battle.
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
 *
 ******************************************************************************/

package lsp.textdocument;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import json.JSONObject;
import lsp.LSPHandler;
import lsp.LSPServerState;
import lsp.Utils;
import rpc.RPCRequest;
import rpc.RPCErrors;
import rpc.RPCMessageList;

public class DefinitionHandler extends LSPHandler
{
	public DefinitionHandler(LSPServerState state)
	{
		super(state);
	}

	@Override
	public RPCMessageList run(RPCRequest request) throws IOException
	{
		try
		{
			JSONObject params = request.get("params");
			JSONObject textDocument = params.get("textDocument");
			File file = Utils.uriToFile(textDocument.get("uri"));
			
			JSONObject position = params.get("position");
			Long line = position.get("line");
			Long col = position.get("character");
			
			return lspServerState.getManager().findDefinition(request, file, line.intValue(), col.intValue());
		}
		catch (URISyntaxException e)
		{
			return new RPCMessageList(request, "URI syntax error");
		}
		catch (Exception e)
		{
			return new RPCMessageList(request, RPCErrors.InternalError, e.getMessage());
		}
	}
}
