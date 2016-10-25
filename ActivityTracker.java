import java.util.ArrayList;

public class ActivityTracker {
	private String name;
	private double timeSpent;
	//private double sumOfTasks;
	public static enum ActivityHierarchy {Root, Project, Task, Activity}
	private ActivityHierarchy hierarchy;
	//private ActivityTracker project;
	//private ActivityTracker task;
	private ActivityTracker parentActivity;
	private ArrayList<ActivityTracker> activities;
	
	//Used to construct a Project, no parent
	public ActivityTracker(String n, double time)
	{
		name = n;
		timeSpent = time;
	}
	
	//Used to construct a Task, with a Project as a parent
	public ActivityTracker(String n, ActivityTracker parent, ActivityHierarchy ah)
	{
		name = n;
		parentActivity = parent;
		activities = new ArrayList<ActivityTracker>();
		hierarchy = ah;
	}
	
	//Used to construct an Activity, with a Task as a parent
	/*public ActivityTracker(String n, double time, ActivityTracker parentTask)
	{
		name = n;
		timeSpent = time;
		task = parentTask;
	}*/
	
	public double getTime()
	{
		return timeSpent;
	}
	
	public void setTime(double hours)
	{
		timeSpent = hours;
	}
	
	public void addTime(double hours)
	{
		timeSpent += hours;
	}
	
	public String getName()
	{
		return name;
	}
	
	public ActivityTracker getProject()
	{
		if(hierarchy == ActivityHierarchy.Activity)
		{
			return getProject().parentActivity;
		}else if(hierarchy == ActivityHierarchy.Task)
		{
			return parentActivity;
		}else
		{
			return this;
		}
	}
	
	public void addActivity(ActivityTracker activity)
	{
		activities.add(activity);
	}
	
	public ArrayList<ActivityTracker> getActivities()
	{
		return activities;	
	}
	
	public void calculateTime()
	{
		double totalProjectHours;
	    double totalTaskHours;
	    double totalDayHours;
	    
		if(hierarchy != ActivityHierarchy.Root)
		{
			System.out.println("Time can only be calculated at Root");
		}else
		{
			totalDayHours = 0;
			for(ActivityTracker project : activities)
			{
				totalProjectHours = 0;
				for(ActivityTracker task : project.activities)
				{
					totalTaskHours = 0;
					for(ActivityTracker act : task.activities)
					{
						totalTaskHours += act.getTime();
					}
					task.setTime(totalTaskHours);
		    		totalProjectHours += totalTaskHours;
				}
				project.setTime(totalProjectHours);
				totalDayHours += totalProjectHours;
			}
			this.setTime(totalDayHours);
		}
	}
	
	//Can only be called from Root
	public ActivityTracker find(ActivityHierarchy type, String nameString)
	{
		for(ActivityTracker project : getActivities())
		{
			if(project.name.equals(nameString) && type == ActivityHierarchy.Project)
			{
				return project;
			}else if(type == ActivityHierarchy.Task)
			{
				for(ActivityTracker task : project.getActivities())
				{
					if(task.name.equals(nameString))
					{
						return task;
					}
				}
			}else if(type == ActivityHierarchy.Activity)
			{
				for(ActivityTracker task : project.getActivities())
				{
					for(ActivityTracker activity : task.getActivities())
					{
						if(activity.name.equals(nameString))
						{
							return activity;
						}
					}
				}
			}
			
//			if(type == ActivityHierarchy.Task || type == ActivityHierarchy.Activity)
//			{
//				for(ActivityTracker task : project.getActivities())
//				{
//					if(type == ActivityHierarchy.Activity)
//					{
//						for(ActivityTracker activity : task.getActivities())
//						{
//							if(activity.name == nameString)
//							{
//								return activity;
//							}
//						}
//					}else if(task.name == nameString)
//					{
//						return task;
//					}
//				}
//			}else if(project.name == nameString)
//			{
//				return project;
//			}
//			System.out.println("Project Name: " + project.name);
//			System.out.println("Compared to: " + nameString);
//		}
		}
		
		return null;
	}
}
