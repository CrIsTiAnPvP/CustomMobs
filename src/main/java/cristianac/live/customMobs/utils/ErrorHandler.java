package cristianac.live.customMobs.utils;

import net.kyori.adventure.text.minimessage.internal.parser.ParsingExceptionImpl;
import org.bukkit.command.CommandSender;

public class ErrorHandler {

    public static void handleException(Exception e, CommandSender sender) {
        if (e instanceof ParsingExceptionImpl) {
            sender.sendMessage(
                    MessageUtils.usermsg.deserialize("<red>Ha ocurrido un error al interpretar colores legacy <aqua><bold>(<gold>&</gold>)</bold></aqua>, por favor, revisa la consola. <blue>(<click:OPEN_URL:https://docs.advntr.dev/minimessage/format.html>Abrir Documentación</click>) <green>para más información")
            );
        } else {
            sender.sendMessage("An error occurred: " + e.getMessage());
        }
        e.printStackTrace();
    }
}
