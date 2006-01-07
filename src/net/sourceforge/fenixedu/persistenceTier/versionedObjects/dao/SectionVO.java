/*
 * Created on May 30, 2005
 */
package net.sourceforge.fenixedu.persistenceTier.versionedObjects.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sourceforge.fenixedu.domain.ExecutionCourse;
import net.sourceforge.fenixedu.domain.Section;
import net.sourceforge.fenixedu.domain.Section;
import net.sourceforge.fenixedu.persistenceTier.ExcepcaoPersistencia;
import net.sourceforge.fenixedu.persistenceTier.IPersistentSection;
import net.sourceforge.fenixedu.persistenceTier.versionedObjects.VersionedObjectsBase;

/**
 * @author mrsp and jdnf
 */
public class SectionVO extends VersionedObjectsBase implements IPersistentSection {

    public List readBySiteAndSection(String executionCourseSigla, String executionPeriodName, String year, Integer superiorSectionID) throws ExcepcaoPersistencia {
        Collection<Section> sections = readAll(Section.class);
        List sections_aux = new ArrayList();
        
        for(Section section : sections){
            ExecutionCourse executionCourse = section.getSite().getExecutionCourse();
            if(executionCourse.getSigla().equals(executionCourseSigla) &&
                    executionCourse.getExecutionPeriod().getName().equals(executionPeriodName) &&
                    executionCourse.getExecutionPeriod().getExecutionYear().getYear().equals(year))
                
                if(superiorSectionID == null){
                    if(section.getSuperiorSection() == null)
                       sections_aux.add(section); 
                }
                else if((section.getSuperiorSection() != null) && 
                        (section.getSuperiorSection().equals(superiorSectionID)))
                    sections_aux.add(section);                                      
        }       
        return sections_aux;
    }
  
    public List readBySite(String executionCourseSigla, String executionPeriodName, String year) throws ExcepcaoPersistencia {
        Collection<Section> sections = readAll(Section.class);
        List sections_aux = new ArrayList();
        
        for(Section section : sections){
            ExecutionCourse executionCourse = section.getSite().getExecutionCourse();
            if(executionCourse.getSigla().equals(executionCourseSigla) &&
                    executionCourse.getExecutionPeriod().getName().equals(executionPeriodName) &&
                    executionCourse.getExecutionPeriod().getExecutionYear().getYear().equals(year))
                
                sections_aux.add(section);                                  
        }       
        return sections_aux;
    }
}
