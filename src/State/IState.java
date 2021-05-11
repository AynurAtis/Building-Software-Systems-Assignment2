package State;


public interface IState {
	
   public void doAction(Context context);
   public Status statu = Status.Not_Started; 

}