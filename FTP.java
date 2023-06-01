

// https://mvnrepository.com/artifact/com.jcraft/jsch
implementation group: 'com.jcraft', name: 'jsch', version: '0.1.55'



import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;


  public void deleteFile(String filePath) {
                String remoteHost = "your-remote-host";
		String username = "your-username";
		String password = "your-password";

		JSch jsch = new JSch();
		Session session = jsch.getSession(username, remoteHost, 22);
		session.setConfig("StrictHostKeyChecking", "no");
		session.setPassword(password);
		session.connect();

		String deleteCommand = "rm " + filePath;


		ChannelExec channel = (ChannelExec) session.openChannel("exec");
		channel.setCommand(deleteCommand);
		channel.connect();

		int exitStatus = channel.getExitStatus();
		if (exitStatus == 0) {
 		   System.out.println("File deleted successfully.");
		} else {
 		   System.out.println("Failed to delete the file.");
		}
		channel.disconnect();
		session.disconnect();
   }
