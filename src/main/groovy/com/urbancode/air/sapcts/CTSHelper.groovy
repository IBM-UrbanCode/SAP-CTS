package com.urbancode.air.sapcts

import com.urbancode.air.AirPluginTool
import com.urbancode.air.CommandHelper

class CTSHelper {
    String sapUrl
    String sapsid
    String clientNumber
    String pf
    String ctsattach
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

    public CTSHelper (
        String sapUrl,
        String clientNumber,
        String sapsid,
        String ctsattach)
    {
        this.sapUrl = sapUrl
        this.clientNumber = clientNumber
        this.sapsid = sapsid
        this.ctsattach = ctsattach
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

    public addToTransport (String username, String password, String filename, String application) {

        helper = new CommandHelper()

        def cmdArgs = [ctsattach]

        cmdArgs << "-url"
        cmdArgs << sapUrl + "/" + clientNumber + "/export_cts_ws"
        cmdArgs << "-f"
        cmdArgs << filename
        cmdArgs << "-sid"
        cmdArgs << sapsid
        cmdArgs << "-a"
        cmdArgs << application

        helper.addEnvironmentVariable("CTS_USER", username)
        helper.addEnvironmentVariable("CTS_PASSWORD", password)

        helper.runCommand("Adding file $filename to transport request", cmdArgs)
    }
}
