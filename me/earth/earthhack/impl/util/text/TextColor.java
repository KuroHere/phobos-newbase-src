// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.text;

public enum TextColor
{
    None {
        @Override
        public String getColor() {
            return "";
        }
    }, 
    Black {
        @Override
        public String getColor() {
            return "§0";
        }
    }, 
    White {
        @Override
        public String getColor() {
            return "§f";
        }
    }, 
    DarkBlue {
        @Override
        public String getColor() {
            return "§1";
        }
    }, 
    DarkGreen {
        @Override
        public String getColor() {
            return "§2";
        }
    }, 
    DarkAqua {
        @Override
        public String getColor() {
            return "§3";
        }
    }, 
    DarkRed {
        @Override
        public String getColor() {
            return "§4";
        }
    }, 
    DarkPurple {
        @Override
        public String getColor() {
            return "§5";
        }
    }, 
    Gold {
        @Override
        public String getColor() {
            return "§6";
        }
    }, 
    Gray {
        @Override
        public String getColor() {
            return "§7";
        }
    }, 
    DarkGray {
        @Override
        public String getColor() {
            return "§8";
        }
    }, 
    Blue {
        @Override
        public String getColor() {
            return "§9";
        }
    }, 
    Green {
        @Override
        public String getColor() {
            return "§a";
        }
    }, 
    Aqua {
        @Override
        public String getColor() {
            return "§b";
        }
    }, 
    Red {
        @Override
        public String getColor() {
            return "§c";
        }
    }, 
    LightPurple {
        @Override
        public String getColor() {
            return "§d";
        }
    }, 
    Yellow {
        @Override
        public String getColor() {
            return "§e";
        }
    }, 
    Obfuscated {
        @Override
        public String getColor() {
            return "§k";
        }
    }, 
    Bold {
        @Override
        public String getColor() {
            return "§l";
        }
    }, 
    Strike {
        @Override
        public String getColor() {
            return "§m";
        }
    }, 
    Underline {
        @Override
        public String getColor() {
            return "§n";
        }
    }, 
    Italic {
        @Override
        public String getColor() {
            return "§o";
        }
    }, 
    Reset {
        @Override
        public String getColor() {
            return "§r";
        }
    }, 
    Rainbow {
        @Override
        public String getColor() {
            return "§y";
        }
    }, 
    RainbowHorizontal {
        @Override
        public String getColor() {
            return "§+";
        }
    }, 
    RainbowVertical {
        @Override
        public String getColor() {
            return "§-";
        }
    };
    
    public static final char SECTIONSIGN = '§';
    public static final String BLACK = "§0";
    public static final String DARK_BLUE = "§1";
    public static final String DARK_GREEN = "§2";
    public static final String DARK_AQUA = "§3";
    public static final String DARK_RED = "§4";
    public static final String DARK_PURPLE = "§5";
    public static final String GOLD = "§6";
    public static final String GRAY = "§7";
    public static final String DARK_GRAY = "§8";
    public static final String BLUE = "§9";
    public static final String GREEN = "§a";
    public static final String AQUA = "§b";
    public static final String RED = "§c";
    public static final String LIGHT_PURPLE = "§d";
    public static final String YELLOW = "§e";
    public static final String WHITE = "§f";
    public static final String OBFUSCATED = "§k";
    public static final String BOLD = "§l";
    public static final String STRIKE = "§m";
    public static final String UNDERLINE = "§n";
    public static final String ITALIC = "§o";
    public static final String RESET = "§r";
    public static final String CUSTOM = "§z";
    public static final String RAINBOW = "§y";
    public static final String RAINBOW_PLUS = "§+";
    public static final String RAINBOW_MINUS = "§-";
    
    public abstract String getColor();
}
