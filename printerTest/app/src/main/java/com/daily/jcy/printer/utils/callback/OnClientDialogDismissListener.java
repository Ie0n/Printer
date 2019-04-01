package com.daily.jcy.printer.utils.callback;

import com.daily.jcy.printer.model.data.bean.Client;

public interface OnClientDialogDismissListener {
    void onCreateListener(Client client);

    void onUpdateListener(Client client);
}
