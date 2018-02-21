import com.urbancode.air.AirPluginTool
import com.urbancode.air.sapcts.CTSHelper

def apTool = new AirPluginTool(this.args[0], this.args[1])
def props = apTool.getStepProperties()

String sapUrl= props['sapUrl'].trim()
String ctsUsername = props['ctsUser'].trim()
String ctsPassword = props['ctsPass']
String clientNumber = props['clientNumber'].trim()
String sapsid = props['sapsid'].trim()
String filename = props['filename'].trim()
String application = props['application'].trim()
String ctsattach = props['ctsattach'].trim()

final def REPO_PATH_SEPARATOR = "/"

while (sapUrl.endsWith(REPO_PATH_SEPARATOR)) {
    sapUrl = sapUrl.substring(0, sapUrl.length() - 1)
}

helper = new CTSHelper(null, sapsid, clientNumber, null, sapUrl, ctsattach)

if (new File(filename).isFile()) {
    helper.addToTransport(ctsUsername, ctsPassword, filename, application)
}

System.exit(0)