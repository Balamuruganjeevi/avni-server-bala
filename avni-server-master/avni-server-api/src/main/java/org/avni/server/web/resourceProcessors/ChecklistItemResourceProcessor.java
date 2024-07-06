package org.avni.server.web.resourceProcessors;

import org.avni.server.domain.ChecklistItem;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class ChecklistItemResourceProcessor extends ResourceProcessor<ChecklistItem>{
    @Override
    public Resource<ChecklistItem> process(Resource<ChecklistItem> resource) {
        ChecklistItem checklistItem = resource.getContent();
        resource.removeLinks();
        resource.add(new Link(checklistItem.getChecklist().getUuid(), "checklistUUID"));
        resource.add(new Link(checklistItem.getChecklistItemDetail().getUuid(), "checklistItemDetailUUID"));
        return resource;
    }
}
