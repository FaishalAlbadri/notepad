package com.faishalbadri.notepad.di;

import com.faishalbadri.notepad.api.local.RoomClient;
import com.faishalbadri.notepad.repository.home.HomeDataLocal;
import com.faishalbadri.notepad.repository.home.HomeRepository;

public class HomeRepositoryInject {
    public static HomeRepository provideTo(RoomClient database) {
        return new HomeRepository(new HomeDataLocal(database));
    }
}
