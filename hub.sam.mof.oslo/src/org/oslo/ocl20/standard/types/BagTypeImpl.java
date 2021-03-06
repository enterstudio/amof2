package org.oslo.ocl20.standard.types;

import java.util.List;

import org.oslo.ocl20.OclProcessor;
import org.oslo.ocl20.semantics.SemanticsVisitor;
import org.oslo.ocl20.semantics.bridge.Classifier;
import org.oslo.ocl20.semantics.bridge.Operation;
import org.oslo.ocl20.semantics.model.types.BagType;
import org.oslo.ocl20.semantics.model.types.CollectionType;
import org.oslo.ocl20.semantics.model.types.OrderedSetType;
import org.oslo.ocl20.semantics.model.types.SequenceType;
import org.oslo.ocl20.semantics.model.types.SetType;
import org.oslo.ocl20.semantics.model.types.TypeFactory;
import org.oslo.ocl20.semantics.model.types.VoidType;
import org.oslo.ocl20.standard.lib.OclBag;


/**
 * @author dha
 *
 */
public class BagTypeImpl extends CollectionTypeImpl implements BagType {
	/** Construct a BagType */
	public BagTypeImpl(Classifier elementType, OclProcessor proc) {
		super(elementType, proc);
		setName("Bag");
	}
	
	/** Set the signatures */
	public void createOperations(TypeFactory tf) {
		getOperations().add( super.processor.getBridgeFactory().buildOperation(tf.buildBooleanType(), "=", new Classifier[] { this }));
		getOperations().add( super.processor.getBridgeFactory().buildOperation(tf.buildBooleanType(), "<>", new Classifier[] { this }));
		
		getOperations().add( super.processor.getBridgeFactory().buildOperation(this, "union", new Classifier[] { this }));
		getOperations().add( super.processor.getBridgeFactory().buildOperation(this, "union", new Classifier[] { tf.buildSetType(this.getElementType()) }));
		getOperations().add( super.processor.getBridgeFactory().buildOperation(this, "union", new Classifier[] { tf.buildOrderedSetType(this.getElementType()) }));
		getOperations().add( super.processor.getBridgeFactory().buildOperation(this, "intersection", new Classifier[] { this }));
		getOperations().add( super.processor.getBridgeFactory().buildOperation(tf.buildSetType(this.getElementType()), "intersection", new Classifier[] { tf.buildSetType(this.getElementType()) }));
		getOperations().add( super.processor.getBridgeFactory().buildOperation(tf.buildOrderedSetType(this.getElementType()), "intersection", new Classifier[] { tf.buildOrderedSetType(this.getElementType()) }));
		getOperations().add( super.processor.getBridgeFactory().buildOperation(this, "including", new Classifier[] { this.getElementType() }));
		getOperations().add( super.processor.getBridgeFactory().buildOperation(this, "excluding", new Classifier[] { this.getElementType() }));
		
		getOperations().add( super.processor.getBridgeFactory().buildOperation(tf.buildIntegerType(), "count", new Classifier[] { this.getElementType() }));
		getOperations().add( super.processor.getBridgeFactory().buildOperation(tf.buildBagType(tf.getFlatType(this)), "flatten", new Classifier[] { }));
		
		getOperations().add( super.processor.getBridgeFactory().buildOperation(this, "asBag", new Classifier[] {} ));
		getOperations().add( super.processor.getBridgeFactory().buildOperation(tf.buildSequenceType(this.getElementType()), "asSequence", new Classifier[] {} ));
		getOperations().add( super.processor.getBridgeFactory().buildOperation(tf.buildSetType(this.getElementType()), "asSet", new Classifier[] {} ));
		getOperations().add( super.processor.getBridgeFactory().buildOperation(tf.buildOrderedSetType(this.getElementType()), "asOrderedSet", new Classifier[] {} ));

		getOperations().add( super.processor.getBridgeFactory().buildOperation(this, "select", new Classifier[] { tf.buildBooleanType() }));
		getOperations().add( super.processor.getBridgeFactory().buildOperation(this, "select", new Classifier[] { getElementType(), tf.buildBooleanType() }));
		getOperations().add( super.processor.getBridgeFactory().buildOperation(this, "reject", new Classifier[] { tf.buildBooleanType() }));
		getOperations().add( super.processor.getBridgeFactory().buildOperation(this, "reject", new Classifier[] { getElementType(), tf.buildBooleanType() }));
		// collectNested's signature will be computed latter
		//_operations.put("collectNested", super.processor.getBridgeFactory().buildOperation(Bag(T), "collectNested", null));
		getOperations().add( super.processor.getBridgeFactory().buildOperation(tf.buildSequenceType(getElementType()), "sortedBy", new Classifier[] { tf.buildClassifier() }));
		getOperations().add( super.processor.getBridgeFactory().buildOperation(tf.buildSequenceType(getElementType()), "sortedBy", new Classifier[] { getElementType(), tf.buildClassifier() }));

		super.createOperations(tf);
	}

	/** Search for an operation with a given signature */
	public Operation lookupOperation(String name, List types) {
		Operation op = (Operation)super.lookupOperation(name, types);
		if (op == null) {
			if (name.equals("collect")) {
				Classifier bodyType = (Classifier)types.get(types.size()-1);
				Classifier baseType = baseElementType(bodyType);
				BagType bagT = super.processor.getTypeFactory().buildBagType( baseType );
				return super.processor.getBridgeFactory().buildOperation( bagT, "collect", null);
			} else if (name.equals("collectNested")) {
				BagType bagT = super.processor.getTypeFactory().buildBagType( (Classifier)types.get(types.size()-1) );
				return super.processor.getBridgeFactory().buildOperation( bagT, "collectNested", null);
			}
		}
		return op;
	}

	/** Check if this (a Bag) conforms with t2 */
	public Boolean conformsTo(Classifier t2) {
		//--- T2 is undefined ---
		if (t2 instanceof VoidType)
			return Boolean.TRUE;
		//--- T2 is BagType ---
		if (t2 instanceof BagType)
			return getElementType().conformsTo(((BagType)t2).getElementType());
		//--- T2 is a CollectionType --
		if (t2 instanceof CollectionType && !(t2 instanceof SetType) && !(t2 instanceof OrderedSetType) && !(t2 instanceof SequenceType)) 
			return getElementType().conformsTo(((CollectionType)t2).getElementType());
		//--- Check for parents ---
		else
			return TypeConformance.isAssignableTo(this, t2) ? Boolean.TRUE : Boolean.FALSE;
	}

	/** Accept a Semantic Visitor */ 
	public Object accept(SemanticsVisitor v, Object obj) {
		return v.visit(this, obj);
	}
	
	/** ToString */
	public String toString(){
		return "Bag("+this.getElementType()+")";
	}
	public Object getDelegate() {
		return java.util.Collection.class;
	}
	public Class getImplClass() {
		return OclBag.class;
	}
}
