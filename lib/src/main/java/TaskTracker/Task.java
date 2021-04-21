package TaskTracker;

public class Task 
{
    private int taskId;
    private int assigner;
    private int reciever;
    private String title;
    private String body;
    //Status: 1=need to be done, 2=submitted, 3=approval, 4=denied
    private int currentStatus;
    private String evidenceLocation;

    public Task(int taskId, int assigner, int reciever, String title, String body, int currentStatus, String evidenceLocation) 
    {
        this.taskId = taskId;
        this.assigner = assigner;
        this.reciever = reciever;
        this.title = title;
        this.body = body;
        this.currentStatus = currentStatus;
        this.evidenceLocation = evidenceLocation;
    }

    public Task(int assigner, int reciever, String title, String body, int currentStatus) {
        this.assigner = assigner;
        this.reciever = reciever;
        this.title = title;
        this.body = body;
        this.currentStatus = currentStatus;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getAssigner() {
        return assigner;
    }

    public void setAssigner(int assigner) {
        this.assigner = assigner;
    }

    public int getReciever() {
        return reciever;
    }

    public void setReciever(int reciever) {
        this.reciever = reciever;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(int currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getEvidenceLocation() {
        return evidenceLocation;
    }

    public void setEvidenceLocation(String evidenceLocation) {
        this.evidenceLocation = evidenceLocation;
    } 
}
