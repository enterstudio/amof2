/**
 *
 *  Class Classifier.java
 *
 *  Generated by KMFStudio at 09 May 2003 17:49:05
 *  Visit http://www.cs.ukc.ac.uk/kmf
 *
 */

package org.oslo.ocl20.semantics.bridge;

import java.util.List;

import org.oslo.ocl20.semantics.SemanticsElement;
import org.oslo.ocl20.semantics.model.types.TypeFactory;


public interface Classifier
extends
    SemanticsElement,
    Namespace,
    ModelElement
{
	public List getProperties();
    public void addProperty(Property p);

	/** Get the 'operations' from 'Classifier' */
	public List getOperations();
	public void addOperation(Operation p);
	/** Set the 'operations' from 'Classifier' */
	public void setOperations(List operations);
	public void createOperations(TypeFactory tf);


	/** Operation  'lookupProperty' from 'Classifier' */
	public Property lookupProperty(String name);

	/** Operation  'lookupOperation' from 'Classifier' */
	public Operation lookupOperation(String name, List types);

	/** Operation  'lookupSignal' from 'Classifier' */
	public Signal lookupSignal(String name, List types);

	public Boolean conformsTo(Classifier c);

	/** Override the toString */
	public String toString();

	/** Clone the object */
	public Object clone();

	public java.lang.Class getImplClass();
	/**
	 * @param name
	 * @return
	 */
	public Property localLookupProperty(String name);
	/**
	 * @return
	 */
	public List getAllSuperTypes();
}
