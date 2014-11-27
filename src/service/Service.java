package service;

import java.io.IOException;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.application.Platform;

import javax.swing.text.BadLocationException;

import model.Channel;
import model.Message;
import model.User;
import model.UserMessage;
import presenter.ChannelPresenter;
import socket.Connection;

/**
 * 
 * @author Steffen Christensen
 */
public class Service {

	static String msg;
	static String[] arr;
	static ChannelPresenter channelPresenter;

	public ChannelPresenter getChannelPresenter() {
		return channelPresenter;
	}

	public void setChannelPresenter(ChannelPresenter channelPresenter) {
		this.channelPresenter = channelPresenter;
	}

	public static void sendPublicMessage(String myMessage) throws IOException, BadLocationException {
		System.out.println("Message from Service! " + myMessage);
		Connection.writer.write("PRIVMSG " + Connection.channel + " :" + myMessage + "\r\n");
		Connection.writer.flush();
	}

	public static void sendPrivateMessage(String myMessage, String to) throws IOException {
		String s = to.substring(1);

		// Main.writer.write("PRIVMSG " + Main.channel + " " + s + " :" +
		// myMessage + "\r\n");
		// Main.writer.flush();
	}

	public static void getOnlineUsers() throws IOException {

		// Main.writer.write("NAMES #datamatiker" + "\r\n");
		// Main.writer.flush();
	}

	public static void fetchUsernames(String line) {

		arr = line.split(" ");
		String temp = "";
		List<User> userList = new ArrayList<User>();
		for (int i = 5; i < arr.length; i++) {
			temp = arr[i].toString();
			final User u = new User();
			u.setNick(temp);
			userList.add(u);
		}
		for (User u : userList)
			addUIUsers(u);
	}

	public static void userJoin(String line) {
		User u = new User();

		String[] findNick = line.split("!");
		String nick = findNick[0].substring(1);
		u.setNick(nick);

		Channel channel = new Channel();
		// channel.setServer(Connection.server);
//		String[] findChannel = line.split(" ");
//		String myChannel = findChannel[2];
//		channel.setName(myChannel);
//		u.getChannels().add(channel);

		addUIUsers(u);
	}

	public static void fetchMessage(String line) throws BadLocationException, IOException {
		System.out.println("Dette er line i fetchMessage " + line);

		String[] arr;
		arr = line.split(" ");
		msg = "";
		for (int i = 3; i < arr.length; i++) {
			msg = msg + " " + arr[i];
		}
		arr = line.split("!");
		String temp = arr[0];
		String userName = temp.substring(1);
		System.out.println("Dette er msg:-------------> " + msg);

		UserMessage message = new UserMessage();
		message.setFrom(userName);
		message.setMessage(msg);
		updateUIMessage(message);

	}

	public static void fetchTopic(String line) {
		String[] arr;
		arr = line.split(" ");
		String topic = "";

		for (int i = 5; i < arr.length; i++) {
			topic = topic + " " + arr[i];
		}
		if (arr.length > 10) {
			// Main.mf.lblTopic.setText(topic.substring(0, 100) + "...");
		} else {
			// Main.mf.lblTopic.setText(topic);
		}
	}

	public static void userLeaving(String line) {
		// fetch all the leaving users SYNTAX: PART <channels> [<message>]
		User u = new User();
		String[] tempArr;
		tempArr = line.split("!");
		String tempStr = tempArr[0];
		String nick = tempStr.substring(1);
		u.setNick(nick);
		// remove the user from the array containing all online users
		List<String> list = Arrays.asList(arr);
		list.remove(nick);
		arr = list.toArray(new String[list.size()]);

		removeUIUsers(u);
		
	}

	public static Message parse(String message) {
		String prefix = null;
		String command = new String();
		String trailing = null;
		String[] parameters = new String[0];

		int prefixEnd = -1;
		int trailingStart = message.length();

		if (message.startsWith(":")) {
			prefixEnd = message.indexOf(" ");
			prefix = message.substring(1, prefixEnd);
		}

		trailingStart = message.indexOf(" :");
		if (trailingStart >= 0) {
			trailing = message.substring(trailingStart + 2);
		} else {
			trailingStart = message.length();
		}

		int start = prefixEnd + 1;
		int end = trailingStart;
		String[] commandAndParameters = message.substring(start, end).split(" ");
		command = commandAndParameters[0];

		if (commandAndParameters.length > 1) {
			parameters = Arrays.copyOfRange(commandAndParameters, 1, commandAndParameters.length, String[].class);
		}

		if (trailing != null) {
			String[] tmp = new String[parameters.length + 1];
			System.arraycopy(parameters, 0, tmp, 0, parameters.length);
			tmp[tmp.length - 1] = trailing;
			parameters = tmp;
		}

		Message m = new Message(prefix, command, parameters, message);
		return m;
	}

	public static void updateUIMessage(final UserMessage m) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				channelPresenter.addMessage(m);
				;
			}
		});

	}

	public static void addUIUsers(final User u) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				channelPresenter.addUser(u);
			}
		});
	}

	public static void removeUIUsers(final User u) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				channelPresenter.removeUser(u);
			}
		});
	}
}
