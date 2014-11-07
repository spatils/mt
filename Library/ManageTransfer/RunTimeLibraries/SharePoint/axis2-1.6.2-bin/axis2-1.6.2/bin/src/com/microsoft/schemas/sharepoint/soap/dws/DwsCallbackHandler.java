
/**
 * DwsCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.microsoft.schemas.sharepoint.soap.dws;

    /**
     *  DwsCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class DwsCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public DwsCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public DwsCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for getDwsMetaData method
            * override this method for handling normal response from getDwsMetaData operation
            */
           public void receiveResultgetDwsMetaData(
                    com.microsoft.schemas.sharepoint.soap.dws.DwsStub.GetDwsMetaDataResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getDwsMetaData operation
           */
            public void receiveErrorgetDwsMetaData(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteFolder method
            * override this method for handling normal response from deleteFolder operation
            */
           public void receiveResultdeleteFolder(
                    com.microsoft.schemas.sharepoint.soap.dws.DwsStub.DeleteFolderResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteFolder operation
           */
            public void receiveErrordeleteFolder(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for findDwsDoc method
            * override this method for handling normal response from findDwsDoc operation
            */
           public void receiveResultfindDwsDoc(
                    com.microsoft.schemas.sharepoint.soap.dws.DwsStub.FindDwsDocResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from findDwsDoc operation
           */
            public void receiveErrorfindDwsDoc(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getDwsData method
            * override this method for handling normal response from getDwsData operation
            */
           public void receiveResultgetDwsData(
                    com.microsoft.schemas.sharepoint.soap.dws.DwsStub.GetDwsDataResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getDwsData operation
           */
            public void receiveErrorgetDwsData(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createFolder method
            * override this method for handling normal response from createFolder operation
            */
           public void receiveResultcreateFolder(
                    com.microsoft.schemas.sharepoint.soap.dws.DwsStub.CreateFolderResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createFolder operation
           */
            public void receiveErrorcreateFolder(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteDws method
            * override this method for handling normal response from deleteDws operation
            */
           public void receiveResultdeleteDws(
                    com.microsoft.schemas.sharepoint.soap.dws.DwsStub.DeleteDwsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteDws operation
           */
            public void receiveErrordeleteDws(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateDwsData method
            * override this method for handling normal response from updateDwsData operation
            */
           public void receiveResultupdateDwsData(
                    com.microsoft.schemas.sharepoint.soap.dws.DwsStub.UpdateDwsDataResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateDwsData operation
           */
            public void receiveErrorupdateDwsData(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createDws method
            * override this method for handling normal response from createDws operation
            */
           public void receiveResultcreateDws(
                    com.microsoft.schemas.sharepoint.soap.dws.DwsStub.CreateDwsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createDws operation
           */
            public void receiveErrorcreateDws(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for removeDwsUser method
            * override this method for handling normal response from removeDwsUser operation
            */
           public void receiveResultremoveDwsUser(
                    com.microsoft.schemas.sharepoint.soap.dws.DwsStub.RemoveDwsUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from removeDwsUser operation
           */
            public void receiveErrorremoveDwsUser(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for canCreateDwsUrl method
            * override this method for handling normal response from canCreateDwsUrl operation
            */
           public void receiveResultcanCreateDwsUrl(
                    com.microsoft.schemas.sharepoint.soap.dws.DwsStub.CanCreateDwsUrlResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from canCreateDwsUrl operation
           */
            public void receiveErrorcanCreateDwsUrl(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for renameDws method
            * override this method for handling normal response from renameDws operation
            */
           public void receiveResultrenameDws(
                    com.microsoft.schemas.sharepoint.soap.dws.DwsStub.RenameDwsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from renameDws operation
           */
            public void receiveErrorrenameDws(java.lang.Exception e) {
            }
                


    }
    