package com.urbancode.air.sapcts

import com.urbancode.air.AirPluginTool
import com.urbancode.air.CommandHelper

class CTSHelper {
    String tp
    String sapUrl
    String sapsid
    String clientNumber
    String pf
    String ctsattach
    CommandHelper helper

    public CTSHelper (
        String tp,
        String sapsid,
        String clientNumber,
        String pf,
        String sapUrl,
        String ctsattach)
    {
        this.tp = tp
        this.sapsid = sapsid
        this.clientNumber = clientNumber
        this.pf = pf
        this.sapUrl = sapUrl
        this.ctsattach = ctsattach
    }

    public addToBuffer (String transportRequest) {

        helper = new CommandHelper()
        def cmdArgs

        if (tp) {
            cmdArgs = [tp]
        }
        else {
            cmdArgs = ["/usr/sap/${sapsid}/SYS/exe/run/tp"]
        }

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

    public importTransports (String transportRequest, Properties props) {

		def leaveTransportRequestInQueueForLaterImport  = props['leaveTransportRequestInQueueForLaterImport']?.toBoolean()
		def importTransportRequestAgain    = props['importTransportRequestAgain']?.toBoolean()
		def overwriteOriginals   = props['overwriteOriginals']?.toBoolean()
		def overwriteObjectsInUnconfirmedRepairs  = props['overwriteObjectsInUnconfirmedRepairs']?.toBoolean()
		def ignoreNonPermittedTransportType  = props['ignoreNonPermittedTransportType']?.toBoolean()
		def ignoreNonPermittedTableClass  = props['ignoreNonPermittedTableClass']?.toBoolean()
		def ignorePredecessorRelations  = props['ignorePredecessorRelations']?.toBoolean()
		def ignoreInvalidComponentVersion  = props['ignoreInvalidComponentVersion']?.toBoolean()
		
        helper = new CommandHelper()
        def cmdArgs

        if (tp) {
            cmdArgs = [tp]
        }
        else {
            cmdArgs = ["/usr/sap/${sapsid}/SYS/exe/run/tp"]
        }

        cmdArgs << "import"
        cmdArgs << "$transportRequest"
        cmdArgs << "$sapsid"
        if (clientNumber) {
            cmdArgs << "client=$clientNumber"
        }

        if (pf) {
            cmdArgs << "pf=$pf"
        }
		
		String options = ""
		
		if(leaveTransportRequestInQueueForLaterImport
			|| importTransportRequestAgain
			|| overwriteOriginals
			|| overwriteObjectsInUnconfirmedRepairs
			|| ignoreNonPermittedTransportType
			|| ignoreNonPermittedTableClass
			|| ignorePredecessorRelations
			|| ignoreInvalidComponentVersion) {
			
			options+="U"
			
		}
		
		if(leaveTransportRequestInQueueForLaterImport) {
			options+="0"
		}
		
		if(importTransportRequestAgain) {
			options+="1"
		}
		
		if(overwriteOriginals) {
			options+="2"
		}
		
		if(overwriteObjectsInUnconfirmedRepairs) {
			options+="6"
		}
		
		if(ignoreNonPermittedTransportType) {
			options+="9"
		}
		
		if(ignoreNonPermittedTableClass) {
			options+="8"
		}
		
		if(ignorePredecessorRelations) {
			options+="3"
		}
		
		if(ignoreInvalidComponentVersion) {
			options+="4"
		}

		if(!options.isEmpty()) {
			cmdArgs << options
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
