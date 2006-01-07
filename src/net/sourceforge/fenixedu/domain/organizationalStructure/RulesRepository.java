/*
 * Created on Oct 4, 2005
 *	by mrsp
 */
package net.sourceforge.fenixedu.domain.organizationalStructure;

import net.sourceforge.fenixedu.domain.Person;

public class RulesRepository {

// NOTA: ALGUNS NOMES NESTE FICHEIRO EST�O EM PORTUGUES E S�O COMO TAL
// DEPENDENTES DO IST E SUA ESTRUTURA. MAS UMA VEZ QUE ESTA CLASSE � APENAS
// REPRESENTATIVA DA ESTRUTURA DO IST FAZ SENTIDO QUE ASSIM SEJA.
// � DE NOTAR QUE NUM FUTURO PROXIMO (?) ESTA CLASSE DEIXAR� DE EXISTIR PARA
// DAR ORIGEM A UM MODELO DE REGRAS E EXCEP��ES APLICADO �S FUN��ES.
    
    public static Boolean isElegible(Person person, Unit unit, String functionName) {
        if (unit.getName().equals("Conselho Directivo")) {
            return isElegibleConselhoDirectivo(person, functionName);
        }
        if (unit.getName().equals("Assembleia de Representantes")) {
            return isElegibleAssembleiaRepresentantes(person, functionName);
        }
//        if (unit.getName().equals("Comiss�o Executiva")) {
//            String parentUnitName = unit.getParentUnit().getName();
//            if (parentUnitName.equals("Conselho Cient�fico")) {
//                return isElegibleConselhoCientifico(person, functionName);
//            }
//            if (parentUnitName.equals("Conselho Pedag�gico")) {
//                return isElegibleConselhoPedagogico(person, functionName);
//            }
//        }
        if (unit.getName().equals("Conselho Administrativo")) {
            return isElegibleConselhoAdministrativo(person, functionName);
        }
        if (unit.getName().equals("Conselho Consultivo")) {
            return isElegibleConselhoConsultivo(person, functionName);
        }
        if (unit.getName().startsWith("Centro de Inform�tica do IST (CIIST)")) {
            return isElegibleCIIST(person, functionName);
        }
        if (unit.getName().startsWith("Presid�ncia do Departamento")) {
            return isElegibleDefaultDepartment(person, functionName);
        }
        if (unit.getName().startsWith("Conselho do Departamento")) {
            return isElegibleDefaultDepartment(person, functionName);
        }
        if (unit.getName().startsWith("Sec��o")) {
            return isElegibleDefaultSection(person, functionName);
        }
        if (unit.getName().equals("Associa��o de Estudantes (AEIST)")) {
            return isElegibleAEIST(person, functionName);
        }
        return true;
    }
    
    private static Boolean isElegibleConselhoDirectivo (Person person, String functionName) {
        // presidente
        if ( functionName.equals("Presidente do IST")) {
            return (isCatedraticTeacher(person) || isAssociatedTeacher(person));
        }
        return true;
    }
  
    private static Boolean isElegibleAssembleiaRepresentantes (Person person, String functionName) {
        if ( functionName.equals("Presidente") ) {
            return (isCatedraticTeacher(person) || isAssociatedTeacher(person));
        }
        if ( functionName.equals("Vice-Presidente") ) {
            //1 ESTUDANTE e 1 PROFESSOR
        }
        if ( functionName.equals("Secret�rio(a)") ) {
            return isEmployee(person);
        }
        if ( functionName.equals("Membro (Docente)")){ 
            return isTeacher(person);
        }
        if ( functionName.equals("Membro (Aluno)")){ 
            return isStudent(person);
        }
        if ( functionName.equals("Membro (N�o Docente)")){ 
            return (isEmployee(person) && !isTeacher(person));
        }
        return true;
    }

//    private static Boolean isElegibleConselhoCientifico (Person person, String functionName) {
//        if (functionName.equals("Presidente-Adjunto para os assuntos cient�ficos")) {
//            return (isCatedraticTeacher(person) || isAssociatedTeacher(person));
//        }
//        if (functionName.equals("Vice-Presidente")) {
//            return (isTeacher(person));
//        }
//        if (functionName.equals("Secret�rio(a)")) {
//            return (isEmployee(person));
//        }
//        return true;
//    }
//    
//    private static Boolean isElegibleConselhoPedagogico (Person person, String functionName) {
//        if (functionName.equals("Presidente-Adjunto para os assuntos pedag�gicos")) {
//            return (isCatedraticTeacher(person) || isAssociatedTeacher(person));
//        }
//        if (functionName.equals("Vice-Presidente Aluno")) {
//            return isStudent(person);
//        }
//        if (functionName.equals("Vogal (Docente)")) {
//            return isTeacher(person);        
//        }
//        if (functionName.equals("Vogal (Aluno)")) {
//            return isStudent(person);
//        }
//        if (functionName.equals("Vogal (Assistente)")) {
//            return isAssistent(person);
//        }
//        return true;
//    }
 
    private static Boolean isElegibleConselhoAdministrativo (Person person, String functionName){
        if (functionName.equals("Presidente-Adjunto para os assuntos administrativos")) {
            return (isCatedraticTeacher(person) || isAssociatedTeacher(person));
        }
        if (functionName.equals("Director De Servi�os De Recursos")) {
            return isEmployee(person);
        }
        if (functionName.equals("Chefe De Reparti��o De Recursos")) {
            return isEmployee(person);
        }
        if (functionName.equals("Vogal Docente")) {
            return isTeacher(person);
        }
        return true;
    }
 
    private static Boolean isElegibleConselhoConsultivo (Person person, String functionName){
        if (functionName.equals("Membro N�o Docente")) {
            return !isTeacher(person);
        }
        return true;
    }
    
    private static Boolean isElegibleCIIST (Person person, String functionName) {
        if (functionName.equals("Presidente")) {
            return isTeacher(person);
        }
        return true;
    }
    
    private static Boolean isElegibleDefaultDepartment (Person person, String functionName) {
        if (functionName.equals("Presidente")) {
            return (isCatedraticTeacher(person) || isAssociatedTeacher(person));
        }
        if (functionName.equals("Docente")) {
            return isTeacher(person);
        }
        return true;
    }
    
    private static Boolean isElegibleDefaultSection (Person person, String functionName) {
        if (functionName.equals("Coordenador") || functionName.equals("Docente")) {
            return  isTeacher(person);
        }
        return true;
    }
    
    private static Boolean isElegibleAEIST (Person person, String functionName) {
            if (functionName.equals("Presidente")) {
                return isStudent(person);
            }
        return true;
    }
    
/************************************************************
 *                AUXILIAR METHODS
 ************************************************************/
    
    private static Boolean isCatedraticTeacher (Person person) {
        if (person.getTeacher() != null 
            && 
            person.getTeacher().getCategory().getLongName().equals("PROFESSOR CATEDRATICO")) {
                return true;
            }
        else {
            return false;
        }
    }
    
    private static Boolean isAssociatedTeacher (Person person) {
        if (person.getTeacher() != null 
            && 
            person.getTeacher().getCategory().getLongName().equals("PROFESSOR ASSOCIADO")) {
                return true;
            }
        else {
            return false;
        }
    }
    
    private static Boolean isStudent (Person person) {
        if(person.getStudentsCount() != 0) {
            return true;
        } 
        else {
            return false;
        }
    }
    
    private static Boolean isTeacher (Person person) {
        if(person.getTeacher() != null
                &&
           person.getTeacher().getCategory().getLongName().startsWith("PROFESSOR")) {
            return true;
        } 
        else {
            return false;
        }
    }
    
    private static Boolean isEmployee (Person person) {
        if(person.getEmployee() != null) {
            return true;
        } 
        else {
            return false;
        }
    }
    
//    private static Boolean isAssistent (Person person) {
//        if(person.getTeacher() != null 
//                &&
//           person.getTeacher().getCategory().getLongName().contains("ASSISTENTE")) {
//            return true;
//        } 
//        else {
//            return false;
//        }
//    }
}

