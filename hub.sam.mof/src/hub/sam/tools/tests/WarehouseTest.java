package hub.sam.tools.tests;

import hub.sam.mof.Repository;
import cmof.Package;
import cmof.reflection.Extent;
import warehouse.*;

public class WarehouseTest {

	public static void main(String[] args) throws Exception {
		Repository repository = Repository.getLocalRepository();

        Extent cmofExtent = repository.getExtent(Repository.CMOF_EXTENT_NAME);
        Package cmofPackage = (Package)cmofExtent.query("Package:cmof");

        Extent warehouseMetaExtent = repository.createExtent("warehouseMetaExtent");
        repository.loadXmiIntoExtent(warehouseMetaExtent, cmofPackage, "resources/models/test/warehouse.xml");
        Package warehousePackage = (Package)warehouseMetaExtent.query("Package:warehouse");
        Extent warehouseExtent = repository.createExtent("warehouseExtent");
        
        warehouseFactory factory = (warehouseFactory)repository.createFactory(warehouseExtent,
                warehousePackage);
        
        Rack a = factory.createRack(); a.setIdentifier("a");
        Rack b = factory.createRack(); b.setIdentifier("b");

        Box a1 = factory.createBox(); a1.setIdentifier("a1");
        Box a2 = factory.createBox(); a2.setIdentifier("a2");
        Box b1 = factory.createBox(); b1.setIdentifier("b1");
        a.getBox().add(a1); a.getBox().add(a2);
        b.getBox().add(b1);

        Item a1A = factory.createItem(); a1A.setIdentifier("a1A"); a1A.setWeight(1);
        Item a2A = factory.createItem(); a2A.setIdentifier("a2A"); a2A.setWeight(2);
        Item a2B = factory.createItem(); a2B.setIdentifier("a2B"); a2B.setWeight(3);
        Item b1A = factory.createItem(); b1A.setIdentifier("b1A"); b1A.setWeight(1);
        a1.getItem().add(a1A);
        a2.getItem().add(a2A); a2.getItem().add(a2B);
        b1.getItem().add(b1A);

        System.out.println("Contents of a2: " + a2.getContent().size());
        System.out.println("Container of a2B: " + a2B.getContainer().getIdentifier());
        System.out.println("Position of a2B: " + a2B.getPosition()); // a.a2.a2B
        System.out.println("Weight of a2: " + a2.getWeight()); // 2+3=5
        
        repository.writeExtentToXmi("resources/models/work/warehouse-test-out.xml", warehousePackage, warehouseExtent);
	}
}
