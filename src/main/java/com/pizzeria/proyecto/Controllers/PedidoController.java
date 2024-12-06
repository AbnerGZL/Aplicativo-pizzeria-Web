package com.pizzeria.proyecto.Controllers;

import com.pizzeria.proyecto.Models.DetallePedido;
import com.pizzeria.proyecto.Models.Pago;
import com.pizzeria.proyecto.Models.Pedido;
import com.pizzeria.proyecto.Models.Proventa;
import com.pizzeria.proyecto.Service.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final DetallePedidoService detallePedidoService;
    private final PagoService pagoService;
    private final ProventaService proventaService;

    @PostMapping("sendorder")
    public String order(@RequestParam Map<String, String> parametros, HttpServletRequest request, HttpSession sesion){
        String idCliente = (String) sesion.getAttribute("id");
        String locate = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("locate".equals(cookie.getName()) && cookie.getValue() != null) {
                    locate = (new String(Base64.getUrlDecoder().decode(cookie.getValue()), StandardCharsets.UTF_8)).trim().replace("%20"," ");
                    break;
                }
            }
        }
//        parametros.forEach((clave, valor) -> {
//            System.out.println("Clave: " + clave + ", Valor: " + valor);
//        });
        if (idCliente != null && locate != null){

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime newTime = now.plusMinutes(30);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String dateTimeString = now.format(formatter);
            String dateOrderString = newTime.format(formatter);

            String codigo = UUID.randomUUID().toString().substring(0,15);

            //Generar nuevo pedido
            Pedido pedido = new Pedido(null, 1, Integer.parseInt(idCliente), dateTimeString, dateOrderString, "pedido", codigo, locate);
//            pedidoService.postPedido(pedido);
            //Obtener ide del nuevo pedido
            Integer idPedido = pedidoService.getPedidoByCodigo(codigo).block().getId_pedido();

            //Generar nuevo DetallePedido
            // Agrupar las claves por sufijo numérico
            Map<Integer, Map<String, String>> grupos = new HashMap<>();
            Pattern pattern = Pattern.compile("-(\\d+)$");

            for (Map.Entry<String, String> entry : parametros.entrySet()) {
                Matcher matcher = pattern.matcher(entry.getKey());
                if (matcher.find()) {
                    int numero = Integer.parseInt(matcher.group(1));
                    grupos.putIfAbsent(numero, new HashMap<>());
                    grupos.get(numero).put(entry.getKey(), entry.getValue());
                }
            }

            // Procesar cada grupo
            double addition = Double.parseDouble(parametros.getOrDefault("addition", "0"));
//            boolean additionApplied = false;

            for (Map.Entry<Integer, Map<String, String>> grupo : grupos.entrySet()) {
                int sufijo = grupo.getKey();
                Map<String, String> valores = grupo.getValue();

                // Obtener valores específicos del grupo
                String proventaKey = "proventa-" + sufijo;
                String priceKey = "proventa-price-" + sufijo;

                if (valores.containsKey(proventaKey) && valores.containsKey(priceKey)) {
                    int proventa = Integer.parseInt(valores.get(proventaKey));
                    double precio = Double.parseDouble(valores.get(priceKey));

                    double subtotal = precio;
//                    if (!additionApplied) {
//                        subtotal += addition;
//                        additionApplied = true;
//                    }

                    // Crear el objeto DetallePedido
                    DetallePedido detalle = new DetallePedido(null, subtotal, idPedido, proventa);
//                    detallePedidoService.postDetalle(detalle);

                    //Editar el estado de los productos venta
                    Proventa prove = new Proventa();
                    System.out.println(proventa);
                    prove.setId_proventa(proventa);
                    prove.setEstado("pedido");
//                    proventaService.putProventa(prove);
                }
            }

            Pago pago = new Pago(null, Double.parseDouble(parametros.get("total")), parametros.get("metodo-pago"), "deuda", idPedido);
//            pagoService.postPago(pago);

            return "redirect:/";
        }
        return "redirect:/car";
    }
}
