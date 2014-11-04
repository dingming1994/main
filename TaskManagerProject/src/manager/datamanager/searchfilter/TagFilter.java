package manager.datamanager.searchfilter;

import data.taskinfo.Tag;
import data.taskinfo.TaskInfo;

//@author A0113011L
public class TagFilter implements Filter{
    public Type getType() {
        return Type.FILTER_TAG;
    }
    
    Tag[] tags;
    
    public boolean filter(TaskInfo task) 
    {
        if (task.tags == null) {
            return false;
        }
        
        for (Tag filterTag : tags) {
            boolean isExist = false;
            for (Tag taskTag : task.tags) {
                if (taskTag.equals(filterTag)) {
                    isExist = true;
                }
            }
            if (!isExist) {
                return false;
            }
        }
        return true;
    }
    
    public TagFilter(Tag[] tags) {
        this.tags = tags;
    }
}
