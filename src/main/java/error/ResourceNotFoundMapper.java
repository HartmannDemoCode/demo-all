package error;

import dto.ErrorDto;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ResourceNotFoundMapper implements ExceptionMapper<ResourceNotFound> {

  @Override
  public Response toResponse(ResourceNotFound ex) {
    return Response.status(404)
        .entity(new ErrorDto(404,ex.getMessage()))
        .build();
  }
}