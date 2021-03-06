/**
 *
 *  Class EnumLiteralExp.java
 *
 *  Generated by KMFStudio at 09 May 2003 17:47:42
 *  Visit http://www.cs.ukc.ac.uk/kmf
 *
 */

package org.oslo.ocl20.semantics.model.expressions;

import org.oslo.ocl20.semantics.SemanticsElement;

public interface EnumLiteralExp
extends
    SemanticsElement,
    LiteralExp,
    OclExpression
{

	/** Get the 'referredEnumLiteral' from 'EnumLiteralExp' */
	public org.oslo.ocl20.semantics.bridge.EnumLiteral getReferredEnumLiteral();
	/** Set the 'referredEnumLiteral' from 'EnumLiteralExp' */
	public void setReferredEnumLiteral(org.oslo.ocl20.semantics.bridge.EnumLiteral referredEnumLiteral);

	/** Override the toString */
	public String toString();

	/** Clone the object */
	public Object clone();
	public String getName();
	public void setName(String enumName);
}
