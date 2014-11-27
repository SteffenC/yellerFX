package socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.text.BadLocationException;

import model.Message;
import service.Service;

public class Connection implements Runnable {

	public static String channel;
	public static String nick;
	public String msg;
	public String login;
	public static String server;
	public static BufferedReader reader;
	public static BufferedWriter writer;

	public Connection(String channel, String nick, String server) throws UnknownHostException, IOException, BadLocationException {
		// The server to connect to and our details.
		this.nick = nick;
		this.channel = channel;
		this.login = nick;
		this.server = server;

	}

	public static void displayMyMessage(String myMessage) throws IOException, BadLocationException {
		// kit.insertHTML(doc, doc.getLength(), "<b>" + nick + "</b>" + " :" +
		// myMessage, 0, 0, HTML.Tag.B);
		// kit.insertHTML(doc, doc.getLength(), "<br>", 0, 0, HTML.Tag.BR);
		// writer.flush();
		// String s = nick.toString();
		// String sendNick = "<b><h2>" + s + "</h2></b>";
		// dlModel.addElement(sendNick);
		// dlModel.addElement(myMessage);
		// ChatFrame.listContent.setModel(dlModel);
		// }
	}
	

	@Override
	public void run() {
		try {
			readLine();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
	}
	
	public void readLine() throws IOException, BadLocationException{


			// TODO: get nick

			// TODO: getLogin (det samme som nick)

			String prevLine;

			// TODO: getChannel The channel which the client will join.

			// Connect directly to the IRC server.

			Socket socket = new Socket(server, 6667);

			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// Log on to the server.

			writer.write("NICK " + nick + "\r\n");

			writer.write("USER " + login + " 8 * : Java IRC Hacks Bot\r\n");
			writer.write("USER " + login + " 8 * : Java IRC Hacks Bot\r\n");

			writer.flush();

			// Read lines from the server until it tells us we have connected.

			String line = null;

			while ((line = reader.readLine()) != null) {

				if (line.indexOf("004") >= 0) {

					// We are now logged in.

					break;

				} else if (line.indexOf("433") >= 0) {
					// TODO: Write username already in use
				}
			}

			// Join the channel.

			writer.write("JOIN " + channel + "\r\n");

			writer.flush();
			// Keep reading lines from the server.

			while ((line = reader.readLine()) != null) {
				prevLine = line;
				System.out.println(line);

				Message message = Service.parse(line);
				String command = message.getCommand();
				switch (command) {
				case "PING":
					writer.write("PONG " + line.substring(5) + "\r\n");
					writer.flush();
					System.out.println("################# A ping was received");
					break;
				case "PRIVMSG":
					Service.fetchMessage(line);
					System.out.println("## IP:#" + Inet4Address.getLocalHost().getHostAddress() + "##");
					System.out.println("################# A priv msg was received");
					System.out.println("################# FULL PRIVMSG" + line);
					break;
				case "JOIN":
					Service.userJoin(line);
					System.out.println("################# A join was received");
					break;
				case "PART":
					Service.userLeaving(line);
					System.out.println("################# A part msg was received");
					break;
				case "MODE":
					System.out.println("################# A mode msg was received");
					break;
				case "NOTICE":
					System.out.println("################# A notice msg was received");
					break;
				case "TOPIC":
					Service.fetchTopic(line);
					System.out.println("################# A topic msg was received");
					break;
				case "KICK":
					System.out.println("################# A kick msg was received");
					break;
				case "BAN":
					System.out.println("################# A ban msg was received");
					break;
				case "353":
					Service.fetchUsernames(line);
					// Service.onJoin(String users, String topic);
					System.out.println("################# A 353 msg was received");
					break;
				default:
					System.out.println("################# Received command: " + command);
					// TODO: send to server output
					break;
				}
			}
		}

}