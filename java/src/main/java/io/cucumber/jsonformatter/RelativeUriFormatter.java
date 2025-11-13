package io.cucumber.jsonformatter;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Function;

final class RelativeUriFormatter implements Function<URI, URI> {
    private final URI base;

    RelativeUriFormatter(URI base) {
        this.base = base;
    }

    @Override
    public URI apply(URI uri) {
        if (!"file".equals(uri.getScheme())) {
            return uri;
        }

        try {
            URI relative = base.relativize(uri);
            // Scheme is lost by relativize
            return new URI("file", relative.getSchemeSpecificPart(), relative.getFragment());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }
}
