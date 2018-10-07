package zamyslov.centreit.testtask.json;

import java.io.Serializable;

public class BasicResponse implements Serializable {
    public enum ResultState { UKNKOWN, ERROR, HANDLED }
    private ResultState resultState;
    private String resultMessage;

    public ResultState getResultState() {
        return resultState;
    }

    public void setResultState(ResultState resultState) {
        this.resultState = resultState;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}
