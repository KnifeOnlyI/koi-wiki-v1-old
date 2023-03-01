package fr.koi.wikiapi.web.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class GraphQlResponse<T> {
    private T data;
}
