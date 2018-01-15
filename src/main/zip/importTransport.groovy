import com.urbancode.air.AirPluginTool
import com.urbancode.air.sapcts.CTSHelper

def apTool = new AirPluginTool(this.args[0], this.args[1])
def props = apTool.getStepProperties()

def transportRequest = props['transportRequest'].trim()
String sapsid = props['sapsid'].trim()
String clientNumber = props['clientNumber'].trim()
String pf = props['pf'].trim()

helper = new CTSHelper(sapsid, clientNumber, pf)

if(new File(transportRequest).isFile()) {
    transportRequest.eachLine { transport ->
        helper.importTransports(transport)
    }
}
else {
    transportRequest.split('\n|,')*.trim().each { transport ->
        helper.importTransports(transport)
    }
}

System.exit(0)