package uebung04.a4;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;
import java.net.*;

public class GetSize
extends JApplet
{
    //  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
    //  |                      Fields                       |   \\
    //  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\

    private JLabel label;

    //  | = - = - = - = - = - /-||=||-\ - = - = - = - = - = |   \\
    //  |            Inserted Modifying Methods             |   \\
    //  | = - = - = - = - = - \-||=||-/ - = - = - = - = - = |   \\

    public void init()
    {
        // just create the gui:
        GridBagLayout layout = new GridBagLayout();
        getContentPane().setLayout(layout);

        label = new JLabel();
        label.setFont(new Font("Verdana", Font.PLAIN, 12));

        TitledBorder border = BorderFactory.createTitledBorder(" Info ");
        border.setTitleFont(label.getFont());
        label.setBorder(border);

        String param = getParameter("URI");
        checkParam(param);

        getContentPane().add(label);
        layout.addLayoutComponent(label, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    }

    private void checkParam(String param)
    {
        param = getDocumentBase() + param;

        try
        {
            URL url = new URI(param).normalize().toURL();

            label.setText(url.toExternalForm() + " hat die Größe: " + url.openConnection().getContentLength() + " bytes");
        }
        catch (MalformedURLException e)
        {
            label.setText(e.toString());
        }
        catch (URISyntaxException e)
        {
            label.setText(e.toString());
        }
        catch (IOException e)
        {
            label.setText(e.toString());
        }
    }
}
