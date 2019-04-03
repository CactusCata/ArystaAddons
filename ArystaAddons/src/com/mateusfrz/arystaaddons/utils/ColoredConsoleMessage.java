package com.mateusfrz.arystaaddons.utils;

public enum ColoredConsoleMessage {

	ANSI_RESET("[0m", 'r'),
	ANSI_BLACK("[30m", '0', '8'),
	ANSI_RED("[31m", '4', 'c'),
	ANSI_GREEN("[32m", '2', 'a'),
	ANSI_YELLOW("[33m", '6', 'e'),
	ANSI_BLUE("[34m", '1','9'),
	ANSI_PURPLE("[35m", '5','d'),
	ANSI_CYAN("[36m", '3', 'b'),
	ANSI_WHITE("[37m", 'f'),

	ANSI_BLACK_BACKGROUND("[40m"),
	ANSI_RED_BACKGROUND("[41m"),
	ANSI_GREEN_BACKGROUND("[42m"),
	ANSI_YELLOW_BACKGROUND("[43m"),
	ANSI_BLUE_BACKGROUND("[44m"),
	ANSI_PURPLE_BACKGROUND("[45m"),
	ANSI_CYAN_BACKGROUND("[46m"),
	ANSI_WHITE_BACKGROUND("[47m");
	
	private final String colorCode;
	private final char[] colorCodesChatColor;
	
	private ColoredConsoleMessage(String colorCode, char... colorCodesChatColor) {
		this.colorCode = colorCode;
		this.colorCodesChatColor = colorCodesChatColor;
	}

	public String getColorCode() {
		return colorCode;
	}
	
	public String getTotalColorCode() {
		return getParagraphCharacter() + getColorCode();
	}

	public char[] getColorCodesChatColor() {
		return colorCodesChatColor;
	}
	
	public static char getParagraphCharacter() {
		return '\u001B';
	}
	
	public static ColoredConsoleMessage getByChatColorCharacter(char car) {
		for(ColoredConsoleMessage ColoredConsoleMessage : ColoredConsoleMessage.values()) {
			for(char cccc : ColoredConsoleMessage.getColorCodesChatColor()) {
				if(cccc == car) {
					return ColoredConsoleMessage;
				}
			}
		}
		return null;
	}
	
	public static String fixString(String message) {
		
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0, lenght = message.length(); i < lenght; i++) {
			if(message.charAt(i) == '§') {
				builder.append(getParagraphCharacter());
				i++;
				builder.append(getByChatColorCharacter(message.charAt(i)).getColorCode());
			} else {
				builder.append(message.charAt(i));
			}
		}
		return builder.toString();
		
	}
	
}
