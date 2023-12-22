package data.constant;

import java.io.*;

public enum Status implements Serializable {
    PLAYABLE, NOTPLAYABLE,
    NORMAL, BOMB, VERTICALBOMB, HORIZONTALBOMB;
}
