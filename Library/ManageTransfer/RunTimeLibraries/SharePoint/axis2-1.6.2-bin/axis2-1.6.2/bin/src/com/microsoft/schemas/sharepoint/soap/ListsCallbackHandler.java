
/**
 * ListsCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.microsoft.schemas.sharepoint.soap;

    /**
     *  ListsCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class ListsCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public ListsCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public ListsCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for getListItems method
            * override this method for handling normal response from getListItems operation
            */
           public void receiveResultgetListItems(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.GetListItemsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getListItems operation
           */
            public void receiveErrorgetListItems(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteAttachment method
            * override this method for handling normal response from deleteAttachment operation
            */
           public void receiveResultdeleteAttachment(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.DeleteAttachmentResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteAttachment operation
           */
            public void receiveErrordeleteAttachment(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateContentTypeXmlDocument method
            * override this method for handling normal response from updateContentTypeXmlDocument operation
            */
           public void receiveResultupdateContentTypeXmlDocument(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.UpdateContentTypeXmlDocumentResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateContentTypeXmlDocument operation
           */
            public void receiveErrorupdateContentTypeXmlDocument(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getListItemChanges method
            * override this method for handling normal response from getListItemChanges operation
            */
           public void receiveResultgetListItemChanges(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.GetListItemChangesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getListItemChanges operation
           */
            public void receiveErrorgetListItemChanges(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteContentTypeXmlDocument method
            * override this method for handling normal response from deleteContentTypeXmlDocument operation
            */
           public void receiveResultdeleteContentTypeXmlDocument(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.DeleteContentTypeXmlDocumentResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteContentTypeXmlDocument operation
           */
            public void receiveErrordeleteContentTypeXmlDocument(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addAttachment method
            * override this method for handling normal response from addAttachment operation
            */
           public void receiveResultaddAttachment(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.AddAttachmentResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addAttachment operation
           */
            public void receiveErroraddAttachment(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getListItemChangesWithKnowledge method
            * override this method for handling normal response from getListItemChangesWithKnowledge operation
            */
           public void receiveResultgetListItemChangesWithKnowledge(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.GetListItemChangesWithKnowledgeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getListItemChangesWithKnowledge operation
           */
            public void receiveErrorgetListItemChangesWithKnowledge(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for undoCheckOut method
            * override this method for handling normal response from undoCheckOut operation
            */
           public void receiveResultundoCheckOut(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.UndoCheckOutResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from undoCheckOut operation
           */
            public void receiveErrorundoCheckOut(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getVersionCollection method
            * override this method for handling normal response from getVersionCollection operation
            */
           public void receiveResultgetVersionCollection(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.GetVersionCollectionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getVersionCollection operation
           */
            public void receiveErrorgetVersionCollection(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateListItemsWithKnowledge method
            * override this method for handling normal response from updateListItemsWithKnowledge operation
            */
           public void receiveResultupdateListItemsWithKnowledge(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.UpdateListItemsWithKnowledgeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateListItemsWithKnowledge operation
           */
            public void receiveErrorupdateListItemsWithKnowledge(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addDiscussionBoardItem method
            * override this method for handling normal response from addDiscussionBoardItem operation
            */
           public void receiveResultaddDiscussionBoardItem(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.AddDiscussionBoardItemResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addDiscussionBoardItem operation
           */
            public void receiveErroraddDiscussionBoardItem(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for checkInFile method
            * override this method for handling normal response from checkInFile operation
            */
           public void receiveResultcheckInFile(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.CheckInFileResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from checkInFile operation
           */
            public void receiveErrorcheckInFile(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addWikiPage method
            * override this method for handling normal response from addWikiPage operation
            */
           public void receiveResultaddWikiPage(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.AddWikiPageResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addWikiPage operation
           */
            public void receiveErroraddWikiPage(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteList method
            * override this method for handling normal response from deleteList operation
            */
           public void receiveResultdeleteList(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.DeleteListResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteList operation
           */
            public void receiveErrordeleteList(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getListCollection method
            * override this method for handling normal response from getListCollection operation
            */
           public void receiveResultgetListCollection(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.GetListCollectionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getListCollection operation
           */
            public void receiveErrorgetListCollection(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createContentType method
            * override this method for handling normal response from createContentType operation
            */
           public void receiveResultcreateContentType(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.CreateContentTypeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createContentType operation
           */
            public void receiveErrorcreateContentType(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateContentType method
            * override this method for handling normal response from updateContentType operation
            */
           public void receiveResultupdateContentType(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.UpdateContentTypeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateContentType operation
           */
            public void receiveErrorupdateContentType(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getListContentType method
            * override this method for handling normal response from getListContentType operation
            */
           public void receiveResultgetListContentType(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.GetListContentTypeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getListContentType operation
           */
            public void receiveErrorgetListContentType(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for checkOutFile method
            * override this method for handling normal response from checkOutFile operation
            */
           public void receiveResultcheckOutFile(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.CheckOutFileResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from checkOutFile operation
           */
            public void receiveErrorcheckOutFile(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addList method
            * override this method for handling normal response from addList operation
            */
           public void receiveResultaddList(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.AddListResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addList operation
           */
            public void receiveErroraddList(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteContentType method
            * override this method for handling normal response from deleteContentType operation
            */
           public void receiveResultdeleteContentType(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.DeleteContentTypeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteContentType operation
           */
            public void receiveErrordeleteContentType(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addListFromFeature method
            * override this method for handling normal response from addListFromFeature operation
            */
           public void receiveResultaddListFromFeature(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.AddListFromFeatureResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addListFromFeature operation
           */
            public void receiveErroraddListFromFeature(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getListContentTypesAndProperties method
            * override this method for handling normal response from getListContentTypesAndProperties operation
            */
           public void receiveResultgetListContentTypesAndProperties(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.GetListContentTypesAndPropertiesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getListContentTypesAndProperties operation
           */
            public void receiveErrorgetListContentTypesAndProperties(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAttachmentCollection method
            * override this method for handling normal response from getAttachmentCollection operation
            */
           public void receiveResultgetAttachmentCollection(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.GetAttachmentCollectionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAttachmentCollection operation
           */
            public void receiveErrorgetAttachmentCollection(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateListItems method
            * override this method for handling normal response from updateListItems operation
            */
           public void receiveResultupdateListItems(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.UpdateListItemsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateListItems operation
           */
            public void receiveErrorupdateListItems(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getListItemChangesSinceToken method
            * override this method for handling normal response from getListItemChangesSinceToken operation
            */
           public void receiveResultgetListItemChangesSinceToken(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.GetListItemChangesSinceTokenResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getListItemChangesSinceToken operation
           */
            public void receiveErrorgetListItemChangesSinceToken(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for applyContentTypeToList method
            * override this method for handling normal response from applyContentTypeToList operation
            */
           public void receiveResultapplyContentTypeToList(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.ApplyContentTypeToListResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from applyContentTypeToList operation
           */
            public void receiveErrorapplyContentTypeToList(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getListAndView method
            * override this method for handling normal response from getListAndView operation
            */
           public void receiveResultgetListAndView(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.GetListAndViewResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getListAndView operation
           */
            public void receiveErrorgetListAndView(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getList method
            * override this method for handling normal response from getList operation
            */
           public void receiveResultgetList(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.GetListResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getList operation
           */
            public void receiveErrorgetList(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateList method
            * override this method for handling normal response from updateList operation
            */
           public void receiveResultupdateList(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.UpdateListResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateList operation
           */
            public void receiveErrorupdateList(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getListContentTypes method
            * override this method for handling normal response from getListContentTypes operation
            */
           public void receiveResultgetListContentTypes(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.GetListContentTypesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getListContentTypes operation
           */
            public void receiveErrorgetListContentTypes(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateContentTypesXmlDocument method
            * override this method for handling normal response from updateContentTypesXmlDocument operation
            */
           public void receiveResultupdateContentTypesXmlDocument(
                    com.microsoft.schemas.sharepoint.soap.ListsStub.UpdateContentTypesXmlDocumentResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateContentTypesXmlDocument operation
           */
            public void receiveErrorupdateContentTypesXmlDocument(java.lang.Exception e) {
            }
                


    }
    