package es.ujaen.ejemplostema4.protocol;

import java.io.IOException;

class ProtocolError extends IOException implements MyProtocol{

    private int type = PROTOCOL_NOERROR;


    public ProtocolError(int t) {
        type = t;
    }

    public int getType() {
        return type;
    }

    @Override
    public String getMessage() {
        switch (type) {
            case PROTOCOL_NOERROR:
                return "NO ERROR";

            case PROTOCOL_MALFORMED_RREQUEST:

                return "BAD REQUEST";
            default:
                return super.getMessage();
        }
    }
}