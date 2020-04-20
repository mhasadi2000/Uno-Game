package com.company;
/**ColorConsole enum is used to print text in different color
 *  in the console.
 *  @author Mohammad Hossein Asadi
 *  @version 18/4/2020**/
public enum ColorConsole {
    RESET("\033[0m"),

    // Regular Colors. Normal color, no bold, background color etc.
    BLACK("\033[0;30m"),    // BLACK
    RED("\033[0;31m"),      // RED
    GREEN("\033[0;32m"),    // GREEN
    YELLOW("\033[0;33m"),   // YELLOW
    BLUE("\033[0;34m"),     // BLUE
    MAGENTA("\033[0;35m"),  // MAGENTA
    CYAN("\033[0;36m"),     // CYAN
    WHITE("\033[0;37m");

    private final String code;

    ColorConsole(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
