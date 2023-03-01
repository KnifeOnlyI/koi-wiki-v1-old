package fr.koi.wikiapi.graphql.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class GQLResponse<T> {
    private T data;
}
