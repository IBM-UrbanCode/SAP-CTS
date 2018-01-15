package com.urbancode.air.sapcts

import com.urbancode.air.AirPluginTool
import com.urbancode.air.CommandHelper

class CTSHelper {
    String sapsid
    String clientNumber
    String pf
    CommandHelper helper

    public CTSHelper (
    String sapsid,
    String clientNumber,
    String pf)
    {
        this.sapsid = sapsid
        this.clientNumber = clientNumber
        this.pf = pf
    }


    public addToBuffer (String transportRequest) {

        helper = new CommandHelper()

        def cmdArgs = ["tp"]

        cmdArgs << "addtobuffer"
        cmdArgs << "$transportRequest"
        cmdArgs << "$sapsid"
        if (clientNumber) {
            cmdArgs << "client=$clientNumber"
        }

        if (pf) {
            cmdArgs << "pf=$pf"
        }

        helper.runCommand("Adding to buffer: '$transportRequest'", cmdArgs)
    }

    public importTransports (String transportRequest) {

        helper = new CommandHelper()

        def cmdArgs = ["tp"]

        cmdArgs << "import"
        cmdArgs << "$transportRequest"
        cmdArgs << "$sapsid"
        if (clientNumber) {
            cmdArgs << "client=$clientNumber"
        }

        if (pf) {
            cmdArgs << "pf=$pf"
        }

        helper.runCommand("Importing: '$transportRequest'", cmdArgs)
    }
}
