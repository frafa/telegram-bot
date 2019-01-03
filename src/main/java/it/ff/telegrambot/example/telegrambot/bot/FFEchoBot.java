package it.ff.telegrambot.example.telegrambot.bot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.vdurmont.emoji.EmojiParser;

@Component
public class FFEchoBot extends TelegramLongPollingBot {

	@Override
	public void onUpdateReceived(Update update) {

		// We check if the update has a message and the message has text
		if (update.hasMessage() && update.getMessage().hasText()) {
			
			String user_first_name = update.getMessage().getChat().getFirstName();
			String user_last_name = update.getMessage().getChat().getLastName();
			String user_username = update.getMessage().getChat().getUserName();
			long user_id = update.getMessage().getChat().getId();
			
			// Set variables
			String message_text = update.getMessage().getText();
			long chat_id = update.getMessage().getChatId();
			String answer = EmojiParser.parseToUnicode(message_text+"\nHere is a smile emoji: :smile:\n\n Here is alien emoji: :alien:");
			
			log(user_first_name+' '+user_last_name, user_username, Long.toString(user_id), message_text, answer);
			
			if (message_text.equals("/pic")) {
				// User sent /pic
				SendPhoto msg = new SendPhoto().setChatId(chat_id).setPhoto("AgADBAAD6q8xG1jPcVHg6yblUImx59XyIRsABI4Sf5R9TfPVw30AAgI")
						.setCaption("Photo");
				try {
					execute(msg); // Call method to send the photo
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			}

			else if (message_text.equals("/markup")) {
				SendMessage message = new SendMessage() // Create a message object object
						.setChatId(chat_id).setText("Here is your keyboard");
				// Create ReplyKeyboardMarkup object
				ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
				// Create the keyboard (list of keyboard rows)
				List<KeyboardRow> keyboard = new ArrayList<>();
				// Create a keyboard row
				KeyboardRow row = new KeyboardRow();
				// Set each button, you can also use KeyboardButton objects if you need something else than text
				row.add("Row 1 Button 1");
				row.add("Row 1 Button 2");
				row.add("Row 1 Button 3");
				// Add the first row to the keyboard
				keyboard.add(row);
				// Create another keyboard row
				row = new KeyboardRow();
				// Set each button for the second line
				row.add("Row 2 Button 1");
				row.add("Row 2 Button 2");
				row.add("Row 2 Button 3");
				// Add the second row to the keyboard
				keyboard.add(row);
				// Set the keyboard to the markup
				keyboardMarkup.setKeyboard(keyboard);
				// Add it to the message
				message.setReplyMarkup(keyboardMarkup);
				try {
					execute(message); // Sending our message object to user
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			} else if (message_text.equals("Row 1 Button 1")) {
				SendPhoto msg = new SendPhoto().setChatId(chat_id).setPhoto("AgADBAAD6q8xG1jPcVHg6yblUImx59XyIRsABI4Sf5R9TfPVw30AAgI")
						.setCaption("Photo");

				try {
					execute(msg); // Call method to send the photo
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			} else if (message_text.equals("/hide")) {
				SendMessage msg = new SendMessage().setChatId(chat_id).setText("Keyboard hidden");
				ReplyKeyboardRemove keyboardMarkup = new ReplyKeyboardRemove();
				msg.setReplyMarkup(keyboardMarkup);
				try {
					execute(msg); // Call method to send the photo
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			}

			else {

				SendMessage message = new SendMessage() // Create a message object object
						.setChatId(chat_id).setText(answer);
				try {
					execute(message); // Sending our message object to user
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			}
		} else if (update.hasMessage() && update.getMessage().hasPhoto()) {
			// Message contains photo
			// Message contains photo
			// Set variables
			long chat_id = update.getMessage().getChatId();

			// Array with photo objects with different sizes
			// We will get the biggest photo from that array
			List<PhotoSize> photos = update.getMessage().getPhoto();
			// Know file_id
			String f_id = photos.stream().sorted(Comparator.comparing(PhotoSize::getFileSize).reversed()).findFirst().orElse(null).getFileId();
			// Know photo width
			int f_width = photos.stream().sorted(Comparator.comparing(PhotoSize::getFileSize).reversed()).findFirst().orElse(null).getWidth();
			// Know photo height
			int f_height = photos.stream().sorted(Comparator.comparing(PhotoSize::getFileSize).reversed()).findFirst().orElse(null).getHeight();
			// Set photo caption
			String caption = "file_id: " + f_id + "\nwidth: " + Integer.toString(f_width) + "\nheight: " + Integer.toString(f_height);
			SendPhoto msg = new SendPhoto().setChatId(chat_id).setPhoto(f_id).setCaption(caption);
			try {
				execute(msg); // Call method to send the photo with caption
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public String getBotUsername() {
		// Return bot username
		return "FFEchoBot";
	}

	@Override
	public String getBotToken() {
		// Return bot token from BotFather
		return "721321299:CCFChi2gfkiBrW2uy7mIJAinkckwaaUy_i0";
	}
	
	private void log(String first_name, String last_name, String user_id, String txt, String bot_answer) {
        System.out.println("\n ----------------------------");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println("Message from " + first_name + " " + last_name + ". (id = " + user_id + ") \n Text - " + txt);
        System.out.println("Bot answer: \n Text - " + bot_answer);
    }

}
