package core.basic;


public class DataTypeDlg extends hub.sam.mof.reflection.ObjectDlg implements DataType
{
    protected DataType self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (DataType)self;
    }

    public core.basic.Package getPackage() {
        return (core.basic.Package)(java.lang.Object)self.getPackage();
    }

    public void setPackage(core.basic.Package value) {
        self.setPackage(value);
    }

    public java.lang.String getName() {
        return self.getName();
    }

    public void setName(java.lang.String value) {
        self.setName(value);
    }

}

