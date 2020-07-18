/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tienda.managedbean;

import com.entidades.session.CargoFacadeLocal;
import com.tienda.entidades.Cargo;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Josselyn Vela
 */
@Named(value = "cargoMangedBean")
@ViewScoped
public class CargoMangedBean implements Serializable, ManagedBeanInterface<Cargo> {

    //paso1 crear instancia
    @EJB
    private CargoFacadeLocal cargoFacadeLocal;

    //CONSTRUCTOR
    public CargoMangedBean() {
    }

    //variable de tipo lista cargos
    private List<Cargo> listaCargos;

    //objeto de tipo cargo
    private Cargo cargo;

    //paso2 inicializar 
    @PostConstruct

    public void init() {

        //lista de los cargos de la bbd
        listaCargos = cargoFacadeLocal.findAll();

    }

    @Override
    public void nuevo() {

        cargo = new Cargo();
    }

    @Override
    public void grabar() {

        try {
            //con esto compruebo si es para crear un nuevo cargo o vamos a editar

            if (cargo.getCodigo() == null) {

                cargoFacadeLocal.create(cargo);
            } else {
                cargoFacadeLocal.edit(cargo);
            }

            cargo = null;
            listaCargos = cargoFacadeLocal.findAll();
            mostrarMensajeTry("INFORMACION OK", FacesMessage.SEVERITY_INFO);

        } catch (Exception e) {
            mostrarMensajeTry("OCURRIO UN ERROR", FacesMessage.SEVERITY_ERROR);
        }
    }

    @Override
    public void seleccionar(Cargo c) {

        cargo = c;

    }

    @Override
    public void eliminar(Cargo c) {

        try {
            cargoFacadeLocal.remove(c);

            listaCargos = cargoFacadeLocal.findAll();
            mostrarMensajeTry("INFORMACION OK", FacesMessage.SEVERITY_INFO);

        } catch (Exception e) {
            mostrarMensajeTry("OCURRIO UN ERROR", FacesMessage.SEVERITY_ERROR);
        }

    }

    @Override
    public void cancelar() {
         
        cargo=null;
    }

    public List<Cargo> getListaCargos(){
        return listaCargos;
    }
    
    public void setListaCargos(List<Cargo> listaCargos){
        this.listaCargos= listaCargos;
    }
    
    public Cargo getCargo(){
        return cargo;
    }
    
    public void setCargo(Cargo cargo){
        this.cargo=cargo;
    }
}
