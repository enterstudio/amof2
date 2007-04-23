/*
A MOF 2 Java -- The MOF Repository tool for Java
Copyright (C) 2005 Markus Scheidgen

    This library is free software; you can redistribute it and/or modify it
under the terms of the GNU Lesser General Public License as published by the
Free Software Foundation; either version 2.1 of the License, or any later
version.

    This library is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
details.

    You should have received a copy of the GNU Lesser General Public License
along with this library; if not, write to the Free Software Foundation, Inc.,
59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/

package hub.sam.mof.mofinstancemodel;

import cmof.Association;
import cmof.Classifier;
import cmof.Element;
import cmof.Operation;
import cmof.Property;
import cmof.UmlClass;
import cmof.reflection.Extent;
import hub.sam.mof.codegeneration.wrapper.OperationWrapper;
import hub.sam.util.MultiMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class MofClassSemantics extends MofClassifierSemantics {

    private final Map<String, Operation> operationNames = new HashMap<String, Operation>();
    private final UmlClass classifier;
    private Extent extent = null;

    protected MofClassSemantics(cmof.UmlClass classifier) {
        super(classifier);
        this.classifier = classifier;
        initialize();
    }

    private static Map<Extent, MultiMap<String, Property>> notNaviagableEnds = new HashMap<Extent, MultiMap<String,Property>>();

    private void addOperation(Operation op) {
        String opName = new OperationWrapper(op).getUnambigousName();
        operationNames.put(opName, op);
    }

    @Override
	protected  void initialize() {
        super.initialize();
        if (classifier == null) {
            return;
        }
        for (Element e: classifier.getMember()) {
            if (e instanceof Operation) {
                Operation op = (Operation)e;
                if (op.getName().equals("getEnabledTransition")) {
                    System.out.println("hallo");
                }
                addOperation(op);
                for (Operation redefinedOp: op.getRedefinedOperation()) {
                	addOperation(redefinedOp);
                }
            }
        }

        this.extent = ((cmof.reflection.Object)classifier).getExtent();
        if (notNaviagableEnds.get(extent) == null) {
            MultiMap<String, Property> ends = new MultiMap<String,Property>();
            notNaviagableEnds.put(extent, ends);
            UmlClass metaClass = ((cmof.reflection.Object)classifier).getMetaClass();
            if (metaClass != null) {
                UmlClass associationM3 = (UmlClass)((cmof.reflection.Object)metaClass).getExtent().query("Package:cmof/Class:Association");
                if (associationM3 != null) {
                    for (cmof.reflection.Object obj: extent.objectsOfType(associationM3, true)) {
                        Association association = (Association)obj;
                        for (Property end: association.getOwnedEnd()) {
                            ends.put(end.getName(), end);
                        }
                    }
                } else {
                    throw new RuntimeException("assert");
                }
            }
        }
    }

    @Override
	public cmof.UmlClass getClassifier() {
        return (cmof.UmlClass)super.getClassifier();
    }

    @Override
	public Operation getFinalOperation(String name) {
        return operationNames.get(name);
    }

    public Collection<? extends Property> getNotNavigablePropertys(String name) {
        MultiMap<String, Property> endsMap = notNaviagableEnds.get(extent);
        Collection<Property> ends = endsMap.get(name);
        Collection<Property> result = new HashSet<Property>();
        for (Property end: ends) {
            Property opposite = end.getOpposite();
            if (classifier.allParents().contains(opposite.getType()) || opposite.getType().equals(classifier)) {
                result.add(end);
            }
        }
        return result;
    }

    @Override
	protected Collection<? extends Property> ownedProperties(Classifier c) {
        return toCollection(((UmlClass)c).getOwnedAttribute());
    }

    @Override
	protected Collection<? extends Classifier> superClasses(Classifier c) {
        return toCollection(((UmlClass)c).getSuperClass());
    }

    // eclipse but, when compiled with eclipse, this method isnt inherited if not overwritten
    @Override
	public Property getProperty(String name) {
        return super.getProperty(name);
    }
}
