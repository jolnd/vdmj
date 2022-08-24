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

package plugins;

import plugins.VDM2UML.Buffers;
import plugins.VDM2UML.UMLGenerator;
import com.fujitsu.vdmj.commands.CommandPlugin;
import com.fujitsu.vdmj.runtime.ClassInterpreter;
import com.fujitsu.vdmj.runtime.Interpreter;
import com.fujitsu.vdmj.tc.definitions.TCClassDefinition;
import com.fujitsu.vdmj.tc.definitions.TCClassList;
import java.io.File;
import java.io.FileWriter;  
import java.io.IOException;


public class Vdm2umlPlugin extends CommandPlugin
{
	public Vdm2umlPlugin(Interpreter interpreter)
	{
		super(interpreter);
	}	

	private StringBuilder boiler = new StringBuilder();

	@Override
	public boolean run(String[] argv) throws Exception
	{
		if (interpreter instanceof ClassInterpreter)
		{
			
			String path = null;
		
			if (argv.length == 2)
			{
				path = argv[1];
			}
			else if (argv.length != 1)
			{
				help();
				return true;
			}
			
			TCClassList classes = interpreter.getTC();

			Buffers buffers = new Buffers(classes);
			
			for (TCClassDefinition cdef: classes)
			{
				cdef.apply(new UMLGenerator(), buffers);
			}
			
			buildBoiler();
			printPlant(path, buffers);
		}
		else
		{
			System.err.println("Only available for VDM++ or VDM-RT");
		}
		
		return true;
	}

	public StringBuilder buildBoiler() 
	{
		boiler.append("@startuml\n\n");
/* 		boiler.append("allow_mixing\n");
		boiler.append("skinparam packageStyle frame\n"); */
		boiler.append("hide empty members\n");
		boiler.append("skinparam Shadowing false\n");
		boiler.append("skinparam classAttributeIconSize 0\n");
		boiler.append("skinparam ClassBorderThickness 0.5\n");
		boiler.append("skinparam class {\n");
		boiler.append("\tBackgroundColor AntiqueWhite\n");
		boiler.append("\tArrowColor Black\n");
		boiler.append("\tBorderColor Black\n}\n");
		boiler.append("skinparam defaultTextAlignment center\n\n");

		return boiler;
	}

	public void printPlant(String path, Buffers buffers)
    {   
        try {
			
			File plantFile = new File(path + "/" + "Model" + ".wsd");
			
			plantFile.createNewFile();
				
			FileWriter writer = new FileWriter(plantFile.getAbsolutePath());
			
			writer.write(boiler.toString());
			writer.write(buffers.defs.toString());
			writer.write(buffers.asocs.toString());
			writer.write("@enduml");
			writer.close();   

            System.out.println("generated PlantUML file");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
	public String help()
	{
		return "vdm2uml - generate UML from VDM++ or VDM-RT";
	}
}
