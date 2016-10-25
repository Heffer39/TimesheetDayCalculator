/**
 * 
 */

/**
 * @author rcerankowski
 *
 *Need a List of Projects
		Each Project needs a list of Tasks
		Each Task needs a list of Activities
		Each Task needs a summarized total # of hours
		Each Project needs a summarized total # of hours
		Each Day needs a summarized total # of hours	
 */

//import java.util.Scanner;
import java.util.Scanner;

public class DayCalculator {
	
	ActivityTracker root;
	//DayCalculator dc;
	
	public DayCalculator()
	{
		this.root = new ActivityTracker("Root", null, ActivityTracker.ActivityHierarchy.Root);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
	    String timeLine;
	    String taskLine;
	    //double totalProjectHours;
	    //double totalTaskHours;
	    
	    DayCalculator dc = new DayCalculator();
	    
	    //System.out.println("Does scanner have next line? " + sc.hasNextLine());
	    
	    //System.out.println("Enter Two Line");
	    timeLine = sc.nextLine();

        //System.out.println("Enter second line");
        taskLine = sc.nextLine();
           	
		dc.parseText(timeLine, taskLine, dc);
		dc.root.calculateTime();
		//dc.calculateTotalHours();
        
		dc.printResults();
	    
	    sc.close();	    
	}
	
	//Read through the text and generate the Project, Task, and Activity data maps
	public void parseText(String timeLine, String taskLine, DayCalculator dc)
	{
		double hours;
		ActivityTracker activity;
		//String project;
		
		//System.out.println("Begin with number?: " + timeLine.startsWith("\\d"));
		//System.out.println("Timeline: " + timeLine);
		//System.out.println("Taskline: " + taskLine);
		
		//if(timeLine.startsWith("\\d"))
	    //{
	    	hours = calculateHours(timeLine);
	    	activity = calculateProjectTask(taskLine, dc);
	    	activity.setTime(hours);
	    	//System.out.println(activity.getTime());
	    //}
	}
	
	//Given the Project, Task, and Activity data - summarize all of the data and calculate the hours for each data grouping
	public double calculateHours(String timeLine)
	{
		String[] bothTimes;
		String[] individualTimes;
		//Start time and end time are captured in minutes
		double startTime;
		double endTime;
		//Hours is returned in hours, in decimal format
		double hours;
		
		bothTimes = timeLine.split(" to ");
		
		//Calculate startTime
		individualTimes = bothTimes[0].split(":");
		startTime = (Double.valueOf(individualTimes[0]) * 60 ) + Double.valueOf(individualTimes[1]);
		
		//Calculate endTime
		individualTimes = bothTimes[1].split(":");
		endTime = (Double.valueOf(individualTimes[0]) * 60 ) + Double.valueOf(individualTimes[1]);
		
		//calculate the hours
		hours = (endTime - startTime) / 60;
		//System.out.println("Hours: " + hours);
		return hours;
		//return a list of strings, for each new line to be added to the "Results"
	}
	
	public ActivityTracker calculateProjectTask(String taskLine, DayCalculator dc)
	{
		String[] taskAndProject;
		//Start time and end time are captured in minutes
		String projectString;
		String taskString;
		ActivityTracker project;
		ActivityTracker task;
		ActivityTracker activity;
		
		taskAndProject = taskLine.split(" : ");
		
		projectString = taskAndProject[0];
		taskString = taskAndProject[1];
		
		//System.out.println("Testing: " + root.getActivities());
		
		if(root.find(ActivityTracker.ActivityHierarchy.Project, projectString) != null)
		{
			//System.out.println("Found a Project!");
			project = root.find(ActivityTracker.ActivityHierarchy.Project, projectString);
			if(project.find(ActivityTracker.ActivityHierarchy.Task, taskString) != null)
			{
				//System.out.println("Found a Task!");
				task = root.find(ActivityTracker.ActivityHierarchy.Task, taskString);
				activity = new ActivityTracker(taskString, task, ActivityTracker.ActivityHierarchy.Activity);
				task.addActivity(activity);
			}else
			{
				//System.out.println("Did not find a Task.");
				task = new ActivityTracker(taskString, project, ActivityTracker.ActivityHierarchy.Task);
				project.addActivity(task);
				activity = new ActivityTracker(taskString, task, ActivityTracker.ActivityHierarchy.Activity);
				task.addActivity(activity);
			}
		}else
		{
			//System.out.println("Did not find a Project.");
			project = new ActivityTracker(projectString, root, ActivityTracker.ActivityHierarchy.Project);
			root.addActivity(project);
			task = new ActivityTracker(taskString, project, ActivityTracker.ActivityHierarchy.Task);
			project.addActivity(task);
			activity = new ActivityTracker(taskString, task, ActivityTracker.ActivityHierarchy.Activity);
			task.addActivity(activity);
		}
		
		/*if(projectMap.get(project) == null)
		{
			projectMap.put(project, new ActivityTracker(project, 0));
			projectTasksMap.put(projectMap.get(project), new HashMap<String, ActivityTracker>());
			activity = projectTasksMap.get(projectMap.get(project)).put(task, newTask(task, project));
			
		}else
		{
			activity = projectTasksMap.get(projectMap.get(project)).put(task, newTask(task, project));
		}*/
		
		return activity;
		
		//return (ActivityTracker)projectTasksMap.get(projectMap.get(project)).get(task).get(
		//	projectTasksMap.get(projectMap.get(project)).get(task).size() - 1);
	}
	
	/*public static ActivityTracker newTask(String task, String project)
	{
		
		
		
		ActivityTracker parentTask;
		ActivityTracker activity;
		//HashMap<ActivityTracker, HashMap<String, ActivityTracker>>
		if(projectTasksMap.get(projectMap.get(project)).get(task) == null)
		{
			parentTask = new ActivityTracker(task, projectMap.get(project));
			activity = new ActivityTracker(task, 0, parentTask);
			parentTask.addActivity(activity);
		}else
		{
			parentTask = projectTasksMap.get(projectMap.get(project)).get(task);
			activity = new ActivityTracker(task, 0, parentTask);
			parentTask.addActivity(activity);
		}
		
		return activity;
	}*/
	
	public void calculateTotalHours()
	{		
		root.setTime(0);
		
		for(ActivityTracker proj : root.getActivities())
		{
			proj.setTime(0);
			for(ActivityTracker task : proj.getActivities())
			{
				task.setTime(0);
				for(ActivityTracker activ : task.getActivities())
				{
					//System.out.println("Activity time! " + activ.getTime());
					task.addTime(activ.getTime());
					//System.out.println("Task! " + task.getName());
				}
				proj.addTime(task.getTime());
			}
			root.addTime(proj.getTime());
		}
		
	}
	
	//Take in a List of Strings and print out each string, line by line
	//Add return characters after each project
	public void printResults()
	{
		/*for(String proj : projectMap.keySet())
	    {
			System.out.println("Project: " + proj + " , Total Hours: " + projectMap.get(proj).getTime());
	    	for(ActivityTracker task : projectTasksMap.keySet())
	    	{
	    		System.out.println("Task: " + task + " , Total Hours: " + 
	    				projectTasksMap.get(projectMap.get(proj)).get(task).getTime());
	    	}
	    }*/	
		System.out.println("Daily total: " + root.getTime());
		
		for(ActivityTracker proj : root.getActivities())
		{
			System.out.println("Project: " + proj.getName() + " - " + proj.getTime());
			for(ActivityTracker task : proj.getActivities())
			{
				System.out.println("\tTask: " + task.getName() + " - " + task.getTime());
			}
		}
	}

}
