package data.constant;

import java.io.*;

public enum Status implements Serializable {
    PLAYABLE, NOTPLAYABLE,
    NORMALPIECE, SPECIALPIECE,
    HAS_ICE_BLOCK, NO_ICE_BLOCK;
}
