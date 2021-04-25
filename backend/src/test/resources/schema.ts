/**
 * This file was auto-generated by openapi-typescript.
 * Do not make direct changes to the file.
 */

export interface paths {
  "/user": {
    post: operations["getUser"];
  };
  "/registration": {
    post: operations["registerNewUser"];
  };
  "/image/{email}": {
    post: operations["uploadImage"];
  };
  "/guest/{email}": {
    get: operations["getFromCache"];
    post: operations["putIntoCache"];
  };
  "/food": {
    post: operations["buyFood"];
  };
  "/fast-track": {
    post: operations["buyFatTrack"];
  };
  "/user/karma": {
    patch: operations["updateKarma"];
  };
  "/guest/{username}": {
    get: operations["userExist"];
  };
  "/guest/hello": {
    get: operations["pangramms"];
  };
  "/mail/unsubscribe/{email}": {
    get: operations["unsubscribe_1"];
    put: operations["unsubscribe_2"];
    post: operations["unsubscribe_6"];
    delete: operations["unsubscribe_4"];
    options: operations["unsubscribe_3"];
    head: operations["unsubscribe_5"];
    patch: operations["unsubscribe"];
  };
}

export interface components {
  schemas: {
    Telegram: {
      userEmail?: string;
      userId: number;
      chatId: number;
      status: "STARTED" | "LOGGED_IN" | "COORDINATES";
      loginTriggerStart?: string;
      isDone: boolean;
      created?: string;
      user?: components["schemas"]["User"];
    };
    User: {
      email?: string;
      nickname?: string;
      firstName?: string;
      lastName?: string;
      karma: number;
      telegram?: components["schemas"]["Telegram"];
      scope: ("GUEST" | "USER" | "TEST" | "ADMIN")[];
      photos: string[];
      fullName?: string;
    };
    TokenInfo: {
      access_token: string;
      token: string;
      refresh_token: string;
      response_type: string;
      redirect_uri: string;
      client_id: string;
      scope: string[];
      token_type: string;
      authorization_endpoint: string;
      userinfo_endpoint: string;
      expires_in: number;
      refresh_token_expires_in: number;
      token_key: string;
      refresh_token_key: string;
      state: string;
    };
  };
}

export interface operations {
  getUser: {
    responses: {
      /** OK */
      200: {
        content: {
          "*/*": components["schemas"]["User"];
        };
      };
      /** Bad Request */
      400: {
        content: {
          "*/*": string;
        };
      };
      /** Not Found */
      404: {
        content: {
          "*/*": string;
        };
      };
      /** Internal Server Error */
      500: {
        content: {
          "*/*": string;
        };
      };
      /** Insufficient Storage */
      507: {
        content: {
          "*/*": string;
        };
      };
    };
  };
  registerNewUser: {
    responses: {
      /** Created */
      201: {
        content: {
          "*/*": components["schemas"]["TokenInfo"];
        };
      };
      /** Bad Request */
      400: {
        content: {
          "*/*": string;
        };
      };
      /** Not Found */
      404: {
        content: {
          "*/*": string;
        };
      };
      /** Internal Server Error */
      500: {
        content: {
          "*/*": string;
        };
      };
      /** Insufficient Storage */
      507: {
        content: {
          "*/*": string;
        };
      };
    };
  };
  uploadImage: {
    parameters: {
      path: {
        email: string;
      };
    };
    responses: {
      /** Created */
      201: {
        content: {
          "*/*": string;
        };
      };
      /** Bad Request */
      400: {
        content: {
          "*/*": string;
        };
      };
      /** Not Found */
      404: {
        content: {
          "*/*": string;
        };
      };
      /** Internal Server Error */
      500: {
        content: {
          "*/*": string;
        };
      };
      /** Insufficient Storage */
      507: {
        content: {
          "*/*": string;
        };
      };
    };
    requestBody: {
      content: {
        "multipart/form-data": {
          file: string;
        };
      };
    };
  };
  getFromCache: {
    parameters: {
      path: {
        email: string;
      };
    };
    responses: {
      /** OK */
      200: {
        content: {
          "*/*": { [key: string]: { [key: string]: any } };
        };
      };
      /** Bad Request */
      400: {
        content: {
          "*/*": string;
        };
      };
      /** Not Found */
      404: {
        content: {
          "*/*": string;
        };
      };
      /** Internal Server Error */
      500: {
        content: {
          "*/*": string;
        };
      };
      /** Insufficient Storage */
      507: {
        content: {
          "*/*": string;
        };
      };
    };
  };
  putIntoCache: {
    parameters: {
      path: {
        email: string;
      };
    };
    responses: {
      /** OK */
      200: unknown;
      /** Bad Request */
      400: {
        content: {
          "*/*": string;
        };
      };
      /** Not Found */
      404: {
        content: {
          "*/*": string;
        };
      };
      /** Internal Server Error */
      500: {
        content: {
          "*/*": string;
        };
      };
      /** Insufficient Storage */
      507: {
        content: {
          "*/*": string;
        };
      };
    };
  };
  buyFood: {
    responses: {
      /** OK */
      200: unknown;
      /** Bad Request */
      400: {
        content: {
          "*/*": string;
        };
      };
      /** Not Found */
      404: {
        content: {
          "*/*": string;
        };
      };
      /** Internal Server Error */
      500: {
        content: {
          "*/*": string;
        };
      };
      /** Insufficient Storage */
      507: {
        content: {
          "*/*": string;
        };
      };
    };
  };
  buyFatTrack: {
    responses: {
      /** OK */
      200: unknown;
      /** Bad Request */
      400: {
        content: {
          "*/*": string;
        };
      };
      /** Not Found */
      404: {
        content: {
          "*/*": string;
        };
      };
      /** Internal Server Error */
      500: {
        content: {
          "*/*": string;
        };
      };
      /** Insufficient Storage */
      507: {
        content: {
          "*/*": string;
        };
      };
    };
  };
  updateKarma: {
    responses: {
      /** OK */
      200: {
        content: {
          "*/*": number;
        };
      };
      /** Bad Request */
      400: {
        content: {
          "*/*": string;
        };
      };
      /** Not Found */
      404: {
        content: {
          "*/*": string;
        };
      };
      /** Internal Server Error */
      500: {
        content: {
          "*/*": string;
        };
      };
      /** Insufficient Storage */
      507: {
        content: {
          "*/*": string;
        };
      };
    };
    requestBody: {
      content: {
        "application/json": number;
      };
    };
  };
  userExist: {
    parameters: {
      path: {
        username: string;
      };
    };
    responses: {
      /** OK */
      200: {
        content: {
          "*/*": boolean;
        };
      };
      /** Bad Request */
      400: {
        content: {
          "*/*": string;
        };
      };
      /** Not Found */
      404: {
        content: {
          "*/*": string;
        };
      };
      /** Internal Server Error */
      500: {
        content: {
          "*/*": string;
        };
      };
      /** Insufficient Storage */
      507: {
        content: {
          "*/*": string;
        };
      };
    };
  };
  pangramms: {
    responses: {
      /** OK */
      200: {
        content: {
          "*/*": string[];
        };
      };
      /** Bad Request */
      400: {
        content: {
          "*/*": string;
        };
      };
      /** Not Found */
      404: {
        content: {
          "*/*": string;
        };
      };
      /** Internal Server Error */
      500: {
        content: {
          "*/*": string;
        };
      };
      /** Insufficient Storage */
      507: {
        content: {
          "*/*": string;
        };
      };
    };
  };
  unsubscribe_1: {
    parameters: {
      path: {
        email: string;
      };
    };
    responses: {
      /** OK */
      200: unknown;
      /** Bad Request */
      400: {
        content: {
          "*/*": string;
        };
      };
      /** Not Found */
      404: {
        content: {
          "*/*": string;
        };
      };
      /** Internal Server Error */
      500: {
        content: {
          "*/*": string;
        };
      };
      /** Insufficient Storage */
      507: {
        content: {
          "*/*": string;
        };
      };
    };
  };
  unsubscribe_2: {
    parameters: {
      path: {
        email: string;
      };
    };
    responses: {
      /** OK */
      200: unknown;
      /** Bad Request */
      400: {
        content: {
          "*/*": string;
        };
      };
      /** Not Found */
      404: {
        content: {
          "*/*": string;
        };
      };
      /** Internal Server Error */
      500: {
        content: {
          "*/*": string;
        };
      };
      /** Insufficient Storage */
      507: {
        content: {
          "*/*": string;
        };
      };
    };
  };
  unsubscribe_6: {
    parameters: {
      path: {
        email: string;
      };
    };
    responses: {
      /** OK */
      200: unknown;
      /** Bad Request */
      400: {
        content: {
          "*/*": string;
        };
      };
      /** Not Found */
      404: {
        content: {
          "*/*": string;
        };
      };
      /** Internal Server Error */
      500: {
        content: {
          "*/*": string;
        };
      };
      /** Insufficient Storage */
      507: {
        content: {
          "*/*": string;
        };
      };
    };
  };
  unsubscribe_4: {
    parameters: {
      path: {
        email: string;
      };
    };
    responses: {
      /** OK */
      200: unknown;
      /** Bad Request */
      400: {
        content: {
          "*/*": string;
        };
      };
      /** Not Found */
      404: {
        content: {
          "*/*": string;
        };
      };
      /** Internal Server Error */
      500: {
        content: {
          "*/*": string;
        };
      };
      /** Insufficient Storage */
      507: {
        content: {
          "*/*": string;
        };
      };
    };
  };
  unsubscribe_3: {
    parameters: {
      path: {
        email: string;
      };
    };
    responses: {
      /** OK */
      200: unknown;
      /** Bad Request */
      400: {
        content: {
          "*/*": string;
        };
      };
      /** Not Found */
      404: {
        content: {
          "*/*": string;
        };
      };
      /** Internal Server Error */
      500: {
        content: {
          "*/*": string;
        };
      };
      /** Insufficient Storage */
      507: {
        content: {
          "*/*": string;
        };
      };
    };
  };
  unsubscribe_5: {
    parameters: {
      path: {
        email: string;
      };
    };
    responses: {
      /** OK */
      200: unknown;
      /** Bad Request */
      400: {
        content: {
          "*/*": string;
        };
      };
      /** Not Found */
      404: {
        content: {
          "*/*": string;
        };
      };
      /** Internal Server Error */
      500: {
        content: {
          "*/*": string;
        };
      };
      /** Insufficient Storage */
      507: {
        content: {
          "*/*": string;
        };
      };
    };
  };
  unsubscribe: {
    parameters: {
      path: {
        email: string;
      };
    };
    responses: {
      /** OK */
      200: unknown;
      /** Bad Request */
      400: {
        content: {
          "*/*": string;
        };
      };
      /** Not Found */
      404: {
        content: {
          "*/*": string;
        };
      };
      /** Internal Server Error */
      500: {
        content: {
          "*/*": string;
        };
      };
      /** Insufficient Storage */
      507: {
        content: {
          "*/*": string;
        };
      };
    };
  };
}
