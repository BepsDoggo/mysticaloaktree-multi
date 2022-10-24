package net.mehvahdjukaar.mysticaloaktree;

import net.mehvahdjukaar.moonlight.api.client.texture_renderer.RenderedTexturesManager;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigBuilder;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigSpec;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigType;

import java.util.function.Supplier;

public class ClientConfigs {

    public static void init() {

    }

    public static ConfigSpec CONFIG_SPEC;

    public static Supplier<Boolean> HAS_TEXT;
    public static Supplier<Boolean> COLORED_TEXT;
    public static Supplier<Boolean> OUTLINE;
    public static Supplier<Boolean> IS_RECOLORED;
    public static Supplier<Boolean> REDUCE_COLORS;
    public static Supplier<Integer> TEXTURE_SIZE;
    public static Supplier<Preset> COLOR_PRESET;

    public enum Preset {
        DEFAULT(""),
        PENCIL("pencil_"),
        PASTEL("pastel_"),
        FLAT("flat_");

        private final String name;

        Preset(String pastel) {
            this.name = pastel;
        }

        public String getName() {
            return name;
        }
    }

    static {
        ConfigBuilder builder = ConfigBuilder.create(MysticalOakTree.res("client"), ConfigType.CLIENT);

        builder.push("general");
        HAS_TEXT = builder.comment("Draws item name on mysticaloaktree").define("draw_item_name", false);
        COLORED_TEXT = builder.comment("If text is enabled, allows it to accept the label dye color")
                .define("colored_text", true);
        TEXTURE_SIZE = builder.comment("Item texture resolution. You might want to keep this multiples of 16")
                .define("texture_resolution", 16, 8, 512);
        builder.pop();
        builder.push("color_settings");

        //var dark = new RGBColor(76 / 255f, 49 / 255f, 19 / 255f, 1);//new RGBColor(64 / 255f, 34 / 255f, 0 / 255f, 1);
        //var light = new RGBColor(243 / 255f, 224 / 255f, 196 / 255f, 1);// new RGBColor(235 / 255f, 213 / 255f, 178 / 255f, 1);

        IS_RECOLORED = builder.comment("Greyscales then recolors each item. You can customize said colors by overriding 'label_colors.png' with a resourcepack")
                .define("recolor_texture", true);
        //DARK_COLOR = builder.comment("First color to use for recoloring. Middle colors are interpolated between the two")
        //        .defineColor("dark_color", dark.toInt());
        // LIGHT_COLOR = builder.comment("Second color to use for recoloring. Middle colors are interpolated between the two")
        //        .defineColor("light_color", light.toInt());
        COLOR_PRESET = builder.comment("picks one of the 3 presets for dyes on mysticaloaktree. " +
                        "This simply changes the texture that is used." +
                        "Requires a resource pack reload" +
                        "Note that you can always change this manually with a resource pack to control all the colors individually")
                .define("color_texture_preset", Preset.PENCIL);
        REDUCE_COLORS = builder.comment("Reduce colors of original image before processing. Makes 3d blocks more 2d like by giving them a limited palette")
                .define("limit_palette", true);
        OUTLINE = builder.comment("Adds an outline to label images").define("outline", true);
        builder.pop();

        builder.onChange(RenderedTexturesManager::clearCache);

        CONFIG_SPEC = builder.buildAndRegister();
    }

}
