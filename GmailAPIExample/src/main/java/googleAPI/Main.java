package googleAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;

public class Main {

	// List Messages
	public static List<Message> listMessages(Gmail service, String userId) throws IOException {

		ListMessagesResponse response = service.users().messages().list(userId).execute();

		List<Message> messages = new ArrayList<Message>();

		while (response.getMessages() != null) {
			messages.addAll(response.getMessages());
			if (response.getNextPageToken() != null) {
				String pageToken = response.getNextPageToken();
				response = service.users().messages().list(userId).setPageToken(pageToken).execute();
			} else {
				break;
			}
		}

		return messages;
	}

	// List Labels
	public static void listLabels(Gmail service, String userId) throws IOException {
		ListLabelsResponse response = service.users().labels().list(userId).execute();
		List<Label> labels = response.getLabels();
		for (Label label : labels) {
			System.out.println(label.toPrettyString());
		}
	}

	public static void main(String[] args) throws IOException {

		// Build a new authorized API client service.
		Gmail service = GmailQuickstart.getGmailService();

		// Get all messages
		List<Message> listMessage = listMessages(service, "me");

		// Print all messages id
		for (Message message : listMessage) {
			System.out.println(message.getId());
		}
		System.out.println();

		// List Labels
		listLabels(service, "me");

	}

}
