package org.example.CarMgmt.Billing.Payments.Methods;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.AbstractComponent;
import com.googlecode.lanterna.gui2.ComponentRenderer;
import com.googlecode.lanterna.gui2.TextGUIGraphics;
import io.nayuki.qrcodegen.QrCode;

public class QRComponent extends AbstractComponent<QRComponent> {
    private final QrCode qr;

    public QRComponent(QrCode qr) {
        this.qr = qr;
    }

    @Override
    protected ComponentRenderer<QRComponent> createDefaultRenderer() {
        return new ComponentRenderer<QRComponent>() {
            @Override
            public TerminalSize getPreferredSize(QRComponent component) {
                // Half as many rows needed since we render 2 pixels vertically per char
                return new TerminalSize(qr.size + 2, (qr.size + 1) / 2 + 2);
            }

            @Override
            public void drawComponent(TextGUIGraphics graphics, QRComponent component) {
                for (int y = 0; y < qr.size; y += 2) {
                    for (int x = 0; x < qr.size; x++) {
                        boolean top = qr.getModule(x, y);
                        boolean bottom = (y + 1 < qr.size) && qr.getModule(x, y + 1);

                        char symbol;
                        if (top && bottom) {
                            symbol = '█';
                        } else if (top) {
                            symbol = '▀';
                        } else if (bottom) {
                            symbol = '▄';
                        } else {
                            symbol = ' ';
                        }

                        graphics.setCharacter(x, y / 2, symbol);
                    }
                }
            }
        };
    }
}
