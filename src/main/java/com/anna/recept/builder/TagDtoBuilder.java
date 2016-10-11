package com.anna.recept.builder;

import com.anna.recept.dto.TagDto;
import com.anna.recept.persist.Tag;

public class TagDtoBuilder {

    private TagDto tag = new TagDto();

    public TagDtoBuilder base(final Tag tagEntity) {
        tag.setId(tagEntity.getId());
        tag.setName(tagEntity.getName());
        return this;
    }

    public TagDto build(){
        return tag;
    }
}
