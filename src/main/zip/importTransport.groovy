import com.urbancode.air.AirPluginTool
import com.urbancode.air.sapcts.CTSHelper

def apTool = new AirPluginTool(this.args[0], this.args[1])
def props = apTool.getStepProperties()

String tp = props['tpLocation'].trim()
def transportRequest = props['transportRequest']
String sapsid = props['sapsid'].trim()
String clientNumber = props['clientNumber'].trim()
String pf = props['pf'].trim()


def helper = new CTSHelper(tp, sapsid, clientNumber, pf, null, null)

if(new File(transportRequest).isFile()) {
    transportRequest.eachLine { transport ->
        helper.importTransports(transport)
    }
}
else {
    transportRequest.split('\n|,')*.trim().each { transport ->
        helper.importTransports(transport, props)
    }
}

System.exit(0)