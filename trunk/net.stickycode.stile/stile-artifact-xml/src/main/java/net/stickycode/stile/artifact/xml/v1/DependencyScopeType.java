
package net.stickycode.stile.artifact.xml.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DependencyScopeType", propOrder = {
    "use"
})
public class DependencyScopeType {

    @XmlElement
    protected List<ArtifactReferenceType> use;

    public List<ArtifactReferenceType> getUse() {
        if (use == null) {
            use = new ArrayList<ArtifactReferenceType>();
        }
        return this.use;
    }

}
