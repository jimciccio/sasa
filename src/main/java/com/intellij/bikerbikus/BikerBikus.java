package com.intellij.bikerbikus;

import com.intellij.bikerbikus.models.Lezione;
import com.intellij.bikerbikus.utils.Utils;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

import java.time.LocalDateTime;

public class I1{
    protected I1(){}
    private static int i=0;
    public static void i(){
        i++;
        TableColumn<Lezione, LocalDateTime> colOrario = null;

        colOrario.setCellFactory(param -> new TableCell<>() {
                    @Override
                    protected void updateItem(LocalDateTime item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item == null ? "" : Utils.formatTime(item.getHour(), item.getMinute()));
                    }
                }
        );    }
}
