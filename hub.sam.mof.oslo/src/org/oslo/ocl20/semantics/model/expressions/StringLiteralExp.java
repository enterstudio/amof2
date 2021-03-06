/**
 *
 *  Class StringLiteralExp.java
 *
 *  Generated by KMFStudio at 09 May 2003 17:47:42
 *  Visit http://www.cs.ukc.ac.uk/kmf
 *
 */

package org.oslo.ocl20.semantics.model.expressions;

import org.oslo.ocl20.semantics.SemanticsElement;

public interface StringLiteralExp
extends
    SemanticsElement,
    LiteralExp,
    OclExpression
{

	/** Get the 'stringSymbol' from 'StringLiteralExp' */
	public String getStringSymbol();
	/** Set the 'stringSymbol' from 'StringLiteralExp' */
	public void setStringSymbol(String stringSymbol);

	/** Override the toString */
	public String toString();

	/** Clone the object */
	public Object clone();
}
