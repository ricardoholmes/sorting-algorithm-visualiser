public interface IController {
    /*
	 * Initialise the controller
     * @param view The view to use
	 */
	void initialise(IView view);

	
	/*
	 * Start the game again - set up view and board appropriately
	 * See requirements - don't forget to set the player number and finished flag to the correct values as well as clearing the board.
	 */
	void startup();
	
	/*
	 * 
	 */
	void update();
}
