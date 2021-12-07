// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.setting.settings;

import me.earth.earthhack.api.setting.*;
import com.google.gson.*;
import me.earth.earthhack.api.setting.event.*;
import java.text.*;

public class NumberSetting<N extends Number> extends Setting<N>
{
    private final boolean restriction;
    private final String description;
    private final boolean floating;
    private final N max;
    private final N min;
    
    public NumberSetting(final String nameIn, final N initialValue) {
        super(nameIn, initialValue);
        final N[] minMax = this.getDefaultMinMax();
        this.min = minMax[0];
        this.max = minMax[1];
        this.restriction = false;
        this.description = this.generateOutPut();
        this.floating = this.isDoubleOrFloat();
    }
    
    public NumberSetting(final String nameIn, final N initialValue, final N min, final N max) {
        super(nameIn, initialValue);
        this.min = min;
        this.max = max;
        this.restriction = true;
        this.description = this.generateOutPut();
        this.floating = this.isDoubleOrFloat();
    }
    
    @Override
    public void fromJson(final JsonElement element) {
        this.setValue(this.numberToValue(element.getAsNumber()));
    }
    
    @Override
    public SettingResult fromString(final String string) {
        if (string == null) {
            return new SettingResult(false, "Value was null.");
        }
        final String noComma = string.replace(',', '.');
        try {
            final Number parsed = NumberFormat.getInstance().parse(noComma);
            final N result = this.numberToValue(parsed);
            if (result == null) {
                return new SettingResult(false, "The numberToValue method returned null.");
            }
            if (this.inBounds(result)) {
                this.setValue(result);
                return SettingResult.SUCCESSFUL;
            }
        }
        catch (ParseException | ClassCastException ex) {
            final Exception ex2;
            final Exception e = ex2;
            e.printStackTrace();
            return new SettingResult(false, string + " could not be parsed.");
        }
        return new SettingResult(false, string + " is out of bounds (" + this.min + "-" + this.max + ")");
    }
    
    @Override
    public String getInputs(final String string) {
        if (string == null || string.isEmpty()) {
            return this.description;
        }
        return "";
    }
    
    @Override
    public void setValue(final N value, final boolean withEvent) {
        if (this.inBounds(value)) {
            super.setValue(value, withEvent);
        }
    }
    
    public boolean inBounds(final N value) {
        return !this.restriction || (value.doubleValue() >= this.min.doubleValue() && value.doubleValue() <= this.max.doubleValue());
    }
    
    public boolean hasRestriction() {
        return this.restriction;
    }
    
    public N getMax() {
        return this.max;
    }
    
    public N getMin() {
        return this.min;
    }
    
    public N numberToValue(final Number number) {
        final Class<? extends Number> type = this.initial.getClass();
        Object result = null;
        if (type == Integer.class) {
            result = number.intValue();
        }
        else if (type == Float.class) {
            result = number.floatValue();
        }
        else if (type == Double.class) {
            result = number.doubleValue();
        }
        else if (type == Short.class) {
            result = number.shortValue();
        }
        else if (type == Byte.class) {
            result = number.byteValue();
        }
        else if (type == Long.class) {
            result = number.longValue();
        }
        return (N)result;
    }
    
    public boolean isFloating() {
        return this.floating;
    }
    
    private N[] getDefaultMinMax() {
        final Class<? extends Number> type = this.initial.getClass();
        final Object[] result = new Object[2];
        if (type == Integer.class) {
            result[0] = Integer.MIN_VALUE;
            result[1] = Integer.MAX_VALUE;
        }
        else if (type == Float.class) {
            result[0] = Float.MIN_VALUE;
            result[1] = Float.MAX_VALUE;
        }
        else if (type == Double.class) {
            result[0] = Double.MIN_VALUE;
            result[1] = Double.MAX_VALUE;
        }
        else if (type == Short.class) {
            result[0] = -32768;
            result[1] = 32767;
        }
        else if (type == Byte.class) {
            result[0] = -128;
            result[1] = 127;
        }
        else {
            result[0] = Long.MIN_VALUE;
            result[1] = Long.MAX_VALUE;
        }
        return (N[])new Number[] { (Number)result[0], (Number)result[1] };
    }
    
    private String generateOutPut() {
        if (this.restriction) {
            return "<" + this.min.toString() + " - " + this.max.toString() + ">";
        }
        return "<-5, 1.0, 10 ... 1337>";
    }
    
    private boolean isDoubleOrFloat() {
        final Class<?> clazz = this.initial.getClass();
        return clazz == Double.class || clazz == Float.class;
    }
}
