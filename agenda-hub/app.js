'use strict';
const $ = (s, r = document) => r.querySelector(s),
  app = $('#app');
const esc = s =>
  String(s ?? '').replace(/[&<>"]/g, c => ({'&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;'})[c]);
const MES = [
  'janeiro',
  'fevereiro',
  'março',
  'abril',
  'maio',
  'junho',
  'julho',
  'agosto',
  'setembro',
  'outubro',
  'novembro',
  'dezembro',
];
const DOW = ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado'];
/* Espaços conforme PDF das salas */
const SP = [
  {id: 'auditorio', nome: 'Auditório', cap: 114, desc: 'Projetor, som e palco', img: 'img/auditorio.jpg'},
  {id: 'reuniao', nome: 'Sala de Reunião', cap: 16, desc: 'TV e mesa de reunião', img: 'img/reuniao.jpg'},
  {id: 'coworking', nome: 'Coworking', cap: 35, desc: 'Estações de trabalho', img: 'img/coworking.jpg'},
  {id: 'reuniao1', nome: 'Sala de Reunião 1', cap: 6, desc: 'Coworking · TV móvel', img: 'img/reuniao1.jpg'},
  {
    id: 'reuniao2',
    nome: 'Sala de Reunião 2',
    cap: 6,
    desc: 'Coworking · mesa e janelas',
    img: 'img/reuniao2.jpg',
  },
  {id: 'preinc', nome: 'Sala Pré-incubação', cap: 34, desc: 'Espaço aberto com TV', img: 'img/preinc.jpg'},
  {id: 'maker', nome: 'Sala Maker VIA', cap: 38, desc: '32 cadeiras e 6 sofás', img: 'img/maker.jpg'},
  {id: 'via', nome: 'Sala VIA hub', cap: 22, desc: '15 cadeiras, 7 sofás, arquibancada', img: 'img/via.jpg'},
  {
    id: 'distrito',
    nome: 'Sala Distrito de Inovação',
    cap: 42,
    desc: '42 cadeiras e TV',
    img: 'img/distrito.jpg',
  },
];
const sp = id => SP.find(s => s.id === id) || SP[0];
const ph = s =>
  `<div class="photo" role="img" aria-label="${esc(s.nome)}"><img src="${s.img}" alt="" onerror="this.remove()"></div>`;
const DEMO = {
  who: 'Ana Souza',
  email: 'ana@startupdemo.com.br',
  empresa: 'Startup Demo Ltda',
  telefone: '(48) 99999-0000',
  cpf: '456.789.000-11',
};
const STAFF = 'Marina Costa';
let ME = null;
const hash = async p => {
  try {
    return [...new Uint8Array(await crypto.subtle.digest('SHA-256', new TextEncoder().encode('inpetu:' + p)))]
      .map(b => b.toString(16).padStart(2, '0'))
      .join('');
  } catch {
    let h = 5381;
    for (const c of 'inpetu:' + p) h = ((h * 33) ^ c.charCodeAt(0)) >>> 0;
    return 'f' + h;
  }
};
const R = (
  id,
  space,
  date,
  start,
  end,
  who,
  email,
  empresa,
  pax,
  reason,
  purpose,
  status,
  created,
  decided,
  x = {},
) => ({
  id,
  space,
  date,
  start,
  end,
  who,
  email,
  empresa,
  cpf: email === DEMO.email ? DEMO.cpf : '',
  telefone: '',
  tipo: 'Startup / empreendedor',
  pax,
  reason,
  purpose,
  status,
  origem: 'Site',
  by: decided ? STAFF : null,
  created,
  decided,
  ...x,
});
async function seed() {
  const B = ['Bruno Lima', 'bruno@inovatech.com', 'Inova Tech'],
    A = [DEMO.who, DEMO.email, DEMO.empresa],
    T = '2026-10-06T15:30';
  const reqs = [
    R(
      0,
      'auditorio',
      '2026-10-05',
      '09:00',
      '12:00',
      ...A,
      60,
      'Encontro de empreendedores',
      'Encontro de empreendedores.',
      'Recusada',
      '2026-10-02T10:10',
      '2026-10-03T09:00',
      {refuse: 'Auditório indisponível nessa data.', origem: 'Indicação'},
    ),
    R(
      1,
      'auditorio',
      '2026-10-08',
      '14:00',
      '16:00',
      ...A,
      80,
      'Evento / workshop',
      'Demo day da startup.',
      'Aprovada',
      T,
      '2026-10-06T18:22',
      {origem: 'Evento'},
    ),
    R(
      2,
      'maker',
      '2026-10-07',
      '10:00',
      '11:00',
      ...A,
      4,
      'Reunião com mentor',
      'Mentoria do produto e próximos passos.',
      'Em análise',
      T,
      null,
    ),
    R(
      3,
      'reuniao2',
      '2026-10-13',
      '14:00',
      '15:00',
      ...B,
      5,
      'Reunião com mentor',
      'Alinhamento do projeto.',
      'Em análise',
      '2026-10-07T09:00',
      null,
    ),
    R(
      4,
      'auditorio',
      '2026-10-14',
      '10:00',
      '12:00',
      'Carlos Mendes',
      'carlos@pmf.sc.gov.br',
      'Secretaria Municipal',
      50,
      'Visita institucional',
      'Visita institucional.',
      'Em análise',
      '2026-10-07T11:40',
      null,
      {origem: 'Indicação'},
    ),
    R(
      5,
      'distrito',
      '2026-10-14',
      '10:00',
      '12:00',
      'Equipe InPETU',
      'hub@inpetu.ufsc.br',
      'InPETU Hub',
      40,
      'Evento do Hub',
      'Conexões do Hub.',
      'Aprovada',
      '2026-10-01T08:00',
      '2026-10-01T09:00',
      {hub: true, origem: 'Evento'},
    ),
    R(
      6,
      'maker',
      '2026-10-20',
      '14:00',
      '17:00',
      ...B,
      10,
      'Oficina',
      'Oficina de projetos.',
      'Aprovada',
      '2026-10-08T10:00',
      '2026-10-08T15:00',
    ),
    R(
      7,
      'coworking',
      '2026-10-27',
      '09:00',
      '18:00',
      ...B,
      3,
      'Trabalho',
      'Dia de coworking.',
      'Aprovada',
      '2026-10-10T10:00',
      '2026-10-12T09:30',
      {origem: 'Site'},
    ),
    R(
      8,
      'reuniao1',
      '2026-10-13',
      '09:00',
      '11:00',
      ...B,
      6,
      'Reunião',
      'Reunião de projeto.',
      'Aprovada',
      '2026-10-09T10:00',
      '2026-10-09T16:00',
    ),
  ];
  const mu = async (id, role, who, email, empresa, cpf, telefone, pw, x = {}) => ({
    id,
    role,
    who,
    email,
    empresa,
    cpf,
    telefone,
    tipo: role === 'eq' ? 'Equipe' : 'Startup / empreendedor',
    origem: 'Site',
    espaco: '',
    pass: await hash(pw),
    created: '2026-09-01T09:00',
    ...x,
  });
  const users = [
    await mu(1, 'eq', STAFF, 'equipe@inpetu.ufsc.br', 'InPETU Hub', '', '(48) 3721-0000', 'inpetu2026'),
  ];
  const log = [];
  reqs.forEach(r => {
    log.push({
      ts: r.created,
      user: r.who,
      action: 'Solicitação criada',
      id: r.id,
      detail: `${sp(r.space).nome} em ${fd(r.date)} ${r.start}–${r.end}`,
    });
    if (r.decided)
      log.push({
        ts: r.decided,
        user: STAFF + ' (equipe)',
        action: r.status === 'Aprovada' ? 'Reserva aprovada' : 'Reserva recusada',
        id: r.id,
        detail: r.refuse || '',
      });
  });
  log.sort((a, b) => b.ts.localeCompare(a.ts));
  return {
    role: 'emp',
    staff: null,
    guest: {...DEMO, tipo: 'Startup / empreendedor', origem: 'Evento'},
    next: 9,
    reqs,
    log,
    users,
  };
}
const KEY = 'inpetu.hub.v4';
let DB;
try {
  DB = JSON.parse(localStorage.getItem(KEY));
} catch {}
const save = () => {
  try {
    localStorage.setItem(KEY, JSON.stringify(DB));
  } catch {}
};
/* utilitários */
const p2 = n => String(n).padStart(2, '0'),
  iso = d => `${d.getFullYear()}-${p2(d.getMonth() + 1)}-${p2(d.getDate())}`;
const fd = d => (d ? d.slice(0, 10).split('-').reverse().join('/') : ''),
  dh = t => (t ? `${fd(t)} ${t.slice(11, 16)}` : '');
const now = () => new Date(Date.now() - new Date().getTimezoneOffset() * 6e4).toISOString().slice(0, 16);
const toM = t => +t.slice(0, 2) * 60 + +t.slice(3, 5),
  hrs = r => (toM(r.end) - toM(r.start)) / 60;
const code = id => '#' + String(id).padStart(3, '0');
const ov = (a, b) => a.space === b.space && a.date === b.date && a.start < b.end && b.start < a.end;
const busy = (x, st = ['Aprovada']) =>
  DB.reqs.filter(r => r.id !== x.id && st.includes(r.status) && ov(r, x));
const prio = r => (/secretaria|prefeitura|munic|p[uú]blic/i.test(r.empresa) ? 'Alta' : 'Normal');
const pill = s =>
  `<span class="pill p-${{Aprovada: 'ok', Recusada: 'no', Cancelada: 'no', 'Em análise': 'wait'}[s]}">${s}</span>`;
const EYE =
  '<svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="vertical-align:middle;margin-right:5px"><path d="M1 12s4-7 11-7 11 7 11 7-4 7-11 7S1 12 1 12z"/><circle cx="12" cy="12" r="3"/></svg>';
const nt = () => (DB.role === 'emp' ? DB.reqs.find(r => r.email === ME.email && r.notify) : null);
const actor = () => ME.who + (DB.role === 'eq' ? ' (equipe)' : '');
const log = (action, id, detail = '') => DB.log.unshift({ts: now(), user: actor(), action, id, detail});
const long = d => {
  const x = new Date(d + 'T12:00');
  return `${DOW[x.getDay()]}, ${x.getDate()} de ${MES[x.getMonth()]}`;
};
/* estado das telas */
const cal = {v: new Date(2026, 9, 1), s: '2026-10-08', f: ''},
  sol = {tab: 'all', q: '', sel: null, flash: null},
  esp = {d: '2026-10-08', s: '09:00', e: '12:00', sel: null, sc: 0},
  apr = {sel: null, err: ''},
  his = {tab: 'hist', from: '', to: '', space: '', status: '', q: ''};
/* ===== AGENDA ===== */
function vAgenda() {
  const y = cal.v.getFullYear(),
    m = cal.v.getMonth(),
    f0 = new Date(y, m, 1),
    n = Math.ceil((f0.getDay() + new Date(y, m + 1, 0).getDate()) / 7) * 7;
  const ev = DB.reqs.filter(r => r.status === 'Aprovada' && (!cal.f || r.space === cal.f));
  let cells = '';
  for (let i = 0; i < n; i++) {
    const d = new Date(y, m, 1 - f0.getDay() + i),
      k = iso(d);
    cells += `<button class="day${d.getMonth() !== m ? ' out' : ''}${k === cal.s ? ' sel' : ''}" data-act="day" data-v="${k}"><span class="n">${d.getDate()}</span>${ev
      .filter(r => r.date === k)
      .map(
        r =>
          `<span class="ev${r.hub ? ' hub' : ''}"><b>${r.start.slice(0, 2)}h · ${esc(sp(r.space).nome)}</b>${esc(r.hub ? r.purpose.replace(/\.$/, '') : r.who.split(' ')[0] + ' · ' + r.empresa)}</span>`,
      )
      .join('')}</button>`;
  }
  const L = ev.filter(r => r.date === cal.s).sort((a, b) => a.start.localeCompare(b.start));
  app.innerHTML = `<section class="head"><div><h1>Agenda de espaços e conexões</h1><p>Encontre um espaço, planeje seu encontro e acompanhe as reservas do Hub.</p></div><button class="btn-primary" data-go="reserva">+ Solicitar reserva</button></section>
 ${nt() ? `<div class="note ok"><b>Reserva ${code(nt().id)} aprovada e publicada na agenda</b>${esc(nt().who)} · ${esc(sp(nt().space).nome)} · ${fd(nt().date)}, das ${nt().start} às ${nt().end}.</div>` : `<section class="banner"><h2>Um lugar para transformar encontros em conexões</h2><p>Auditório, salas de reunião e coworking. As reservas são confirmadas após análise da equipe.</p></section>`}
 <div class="grid"><section class="card cal"><div class="cal__top"><h2>${MES[m][0].toUpperCase() + MES[m].slice(1)} ${y}</h2><div class="cal__nav"><button class="btn-sq" data-act="prev" aria-label="Mês anterior">‹</button><button class="btn-out" data-act="today">Hoje</button><button class="btn-sq" data-act="next" aria-label="Próximo mês">›</button><button class="btn-primary btn-sm">Mês</button></div></div>
 <div class="cal__filter"><select data-chg="calf" aria-label="Espaço"><option value="">Todos os espaços</option>${SP.map(s => `<option value="${s.id}"${cal.f === s.id ? ' selected' : ''}>${s.nome}</option>`).join('')}</select><span class="tz">Horários exibidos em Brasília</span></div>
 <div class="dow">${['DOM', 'SEG', 'TER', 'QUA', 'QUI', 'SEX', 'SÁB'].map(d => `<span>${d}</span>`).join('')}</div><div class="days">${cells}</div>
 <div class="legend"><span><i class="dot"></i>Reserva aprovada</span><span><i class="dot dot--hub"></i>Evento do Hub</span></div></section>
 <aside class="card side"><div class="kicker">DETALHES DO DIA</div><h3>${long(cal.s)}</h3><p class="count">${L.length ? L.length + ' reserva' + (L.length > 1 ? 's' : '') + ' confirmada' + (L.length > 1 ? 's' : '') : 'Nenhuma reserva'}</p>
 ${L.length ? ph(sp(L[0].space)) : '<p class="empty">Dia livre. Use “Solicitar reserva” para pedir um espaço.</p>'}
 ${L.map(
   r => `<div class="item"><div class="item__head"><h4>${esc(sp(r.space).nome)}</h4>${pill('Aprovada')}</div><p class="when">${r.start} – ${r.end} · ${r.pax} participantes</p>
 ${[
   ['RESPONSÁVEL', r.who],
   ['EMPRESA', r.empresa],
   ['MOTIVO', r.reason],
   ['FINALIDADE', r.purpose],
 ]
   .map(([a, b]) => `<div class="f"><small>${a}</small><span>${esc(b)}</span></div>`)
   .join('')}</div>`,
 ).join('')}</aside></div>`;
}
/* ===== ESPAÇOS (carrossel) ===== */
function vEsp() {
  const q = {date: esp.d, start: esp.s, end: esp.e};
  const st = s => {
    const b = busy({...q, space: s.id});
    if (b.length) return ['no', `Ocupada · ${b[0].start}–${b[0].end}`];
    return busy({...q, space: s.id}, ['Em análise']).length
      ? ['wait', 'Livre · há pedido em análise']
      : ['ok', 'Livre no horário'];
  };
  let det = '<p class="empty">Clique em um espaço para ver a disponibilidade hora a hora.</p>';
  if (esp.sel) {
    const s = sp(esp.sel),
      day = DB.reqs
        .filter(r => r.space === s.id && r.date === esp.d && ['Aprovada', 'Em análise'].includes(r.status))
        .sort((a, b) => a.start.localeCompare(b.start));
    const sl = [...Array(10)]
      .map((_, i) => {
        const x = {space: s.id, date: esp.d, start: p2(i + 8) + ':00', end: p2(i + 9) + ':00'};
        return `<span class="sl ${busy(x).length ? 'no' : busy(x, ['Em análise']).length ? 'wait' : 'ok'}">${p2(i + 8)}h</span>`;
      })
      .join('');
    det = `<section class="card dtl">${ph(s)}<div><h2>${s.nome}</h2><p class="sub">${s.cap} lugares · ${s.desc}</p><b style="font-size:12px">${long(esp.d)} · ${fd(esp.d)}</b><div class="slots">${sl}</div>
  <p class="tz">Verde: livre · Amarelo: pedido em análise · Vermelho: ocupado</p>
  ${day.length ? `<table class="tb"><tr><th>HORÁRIO</th><th>SITUAÇÃO</th><th>${DB.role === 'eq' ? 'RESPONSÁVEL' : 'DETALHE'}</th></tr>${day.map(r => `<tr><td>${r.start} – ${r.end}</td><td>${r.status === 'Aprovada' ? pill('Aprovada').replace('Aprovada', 'Ocupada').replace('p-ok', 'p-no') : pill('Em análise')}</td><td>${DB.role === 'eq' ? esc(r.who + ' · ' + r.empresa) : 'Reservado'}</td></tr>`).join('')}</table>` : '<p class="empty">Nenhuma reserva neste dia: livre o dia todo.</p>'}
  <div class="actions"><button class="btn-primary" data-go="reserva?space=${s.id}&date=${esp.d}&start=${esp.s}&end=${esp.e}">Solicitar este espaço</button></div></div></section>`;
  }
  app.innerHTML = `<section class="head"><div><h1>Espaços do Hub</h1><p>Veja quais espaços estão livres ou ocupados em uma data e horário e solicite a reserva.</p></div></section>
 <div class="card filt"><label>Data<input type="date" data-chg="ed" value="${esp.d}"></label><label>Início<input type="time" data-chg="es" value="${esp.s}"></label><label>Término<input type="time" data-chg="ee" value="${esp.e}"></label></div>
 <div class="car"><button class="btn-sq" data-act="scroll" data-v="-1" aria-label="Anterior">‹</button><div class="track">${SP.map(
   s => {
     const [t, l] = st(s);
     return `<button class="scard${esp.sel === s.id ? ' on' : ''}" data-act="sel-esp" data-v="${s.id}">${ph(s)}<h4>${s.nome}</h4><p>${s.cap} lugares · ${s.desc}</p><span class="pill p-${t}">${l}</span></button>`;
   },
 ).join(
   '',
 )}</div><button class="btn-sq" data-act="scroll" data-v="1" aria-label="Próximo">›</button></div>${det}`;
  const t = $('.track');
  t.scrollLeft = esp.sc;
  t.onscroll = () => (esp.sc = t.scrollLeft);
}
/* ===== NOVA RESERVA ===== */
function vRes(p) {
  const u = DB.role === 'emp' ? ME : {},
    sid = p.get('space') || 'maker',
    v = (k, d) => esc(p.get(k) || d);
  app.innerHTML = `<p class="tz" style="margin-bottom:8px">Agenda / Nova reserva</p><section class="head"><div><h1>Vamos preparar seu próximo encontro?</h1><p>Cadastre seus dados e envie sua solicitação. A confirmação acontece após a aprovação da equipe.</p></div></section><div class="steps"><span>01 Escolha o espaço</span><span class="on">02 Informe seus dados e horários</span><span>03 Aguarde a aprovação da equipe</span></div>
 <form id="f" class="grid"><div><section class="card"><h2>1. Escolha o espaço</h2><p class="sub">Disponibilidade conforme data e horário informados abaixo.</p><div class="picks">${SP.map(s => `<label class="pick"><input type="radio" name="space" value="${s.id}"${s.id === sid ? ' checked' : ''}>${ph(s)}<b>${s.nome}</b><small>${s.cap} pessoas · ${s.desc}</small><span class="pf"><em class="sel"></em><i class="av" data-s="${s.id}"></i></span></label>`).join('')}</div></section>
 <section class="card" style="margin-top:14px"><h2>2. Seus dados e sua reserva</h2><p class="sub">Os dados do cadastro serão usados para acompanhar suas solicitações (LGPD).</p><div class="fg">
 <label>Nome completo *<input name="who" required value="${esc(u.who || '')}"></label><label>E-mail *<input name="email" type="email" required value="${esc(u.email || '')}"></label>
 <label>CPF (somente números) *<input name="cpf" required inputmode="numeric" placeholder="000.000.000-00" value="${esc(u.cpf || '')}"></label><label>Empresa / organização *<input name="empresa" required value="${esc(u.empresa || '')}"></label>
 <label>Telefone de contato *<input name="telefone" required value="${esc(u.telefone || '')}"></label><label>Tipo de solicitante *<select name="tipo">${['Startup / empreendedor', 'Aluno', 'Professor', 'Empresa residente', 'Outro'].map(o => `<option${o === u.tipo ? ' selected' : ''}>${o}</option>`).join('')}</select></label>
 <label>Data da reserva *<input name="date" type="date" required value="${v('date', '2026-10-07')}"></label><label>Início *<input name="start" type="time" required value="${v('start', '10:00')}"></label>
 <label>Término *<input name="end" type="time" required value="${v('end', '11:00')}"></label><label>Participantes *<input name="pax" type="number" min="1" required value="4"></label>
 <label>Motivo da visita *<select name="reason">${['Reunião com mentor', 'Evento / workshop', 'Reunião', 'Oficina', 'Trabalho', 'Outro'].map(o => `<option>${o}</option>`).join('')}</select></label><label>Como conhece o Hub? *<select name="origem">${['Site', 'Indicação', 'Evento', 'Outro'].map(o => `<option${o === u.origem ? ' selected' : ''}>${o}</option>`).join('')}</select></label>
 <label class="w">Finalidade *<textarea name="purpose" rows="3" required placeholder="Ex.: Mentoria do produto e próximos passos.">${u.email === DEMO.email ? 'Mentoria do produto e próximos passos.' : ''}</textarea></label></div><small class="tz">* Campos obrigatórios</small></section></div>
 <aside><section class="card side"><h3 style="margin-top:0">Sua reserva</h3><p class="count">Confira antes de enviar</p><div id="sum"></div>
 <div class="how"><b>Como funciona?</b><br>1. Envie o pedido com seus dados.<br>2. A equipe analisa a solicitação.<br>3. Após aprovada, a reserva aparece na agenda e em suas solicitações.</div></aside></div>
 <section class="card" style="margin-top:14px"><label class="chk"><input type="checkbox" name="ok" required> Concordo com o uso dos meus dados para o cadastro e a gestão desta reserva. O envio não garante a reserva do espaço.</label><p id="err" class="note err" hidden></p><div class="actions"><button type="button" class="btn-ghost" data-go="agenda">Cancelar</button><button class="btn-primary">Enviar solicitação</button></div></section></form>`;
  const f = $('#f'),
    data = () => Object.fromEntries(new FormData(f));
  const sum = () => {
    const d = data(),
      s = sp(d.space),
      x = {space: d.space, date: d.date, start: d.start, end: d.end};
    let box;
    document.querySelectorAll('.av').forEach(a => {
      const b = busy({space: a.dataset.s, date: d.date, start: d.start, end: d.end}).length;
      a.textContent = b ? 'Ocupado no horário' : 'Livre no horário';
      a.className = 'av ' + (b ? 'no' : 'ok');
    });
    if (d.start && d.end && d.end <= d.start)
      box = ['err', '<b>Horário inválido</b>O término precisa ser depois do início.'];
    else if (busy(x).length)
      box = [
        'err',
        `<b>Horário ocupado</b>Já existe reserva aprovada das ${busy(x)[0].start} às ${busy(x)[0].end}.`,
      ];
    else if (+d.pax > s.cap) box = ['err', `<b>Capacidade excedida</b>${s.nome} comporta ${s.cap} pessoas.`];
    else if (busy(x, ['Em análise']).length)
      box = ['ok', '<b>Disponível para solicitar</b>Nenhuma reserva confirmada neste horário. Há outro pedido em análise.'];
    else box = ['ok', '<b>Disponível para solicitar</b>Nenhuma reserva confirmada neste horário.'];
    $('#sum').innerHTML =
      `${ph(s)}<h3>${s.nome}</h3><p class="count">${s.cap} lugares · InPETU Hub</p><div class="note ${box[0]}">${box[1]}</div>${[
        ['DATA', fd(d.date)],
        ['HORÁRIO', `${d.start} – ${d.end} · Horário de Brasília`],
        ['PARTICIPANTES', d.pax + ' pessoas'],
        ['RESPONSÁVEL', (d.who || '—') + ' · ' + (d.empresa || '—')],
      ]
        .map(([a, b]) => `<div class="f"><small>${a}</small><span>${esc(b)}</span></div>`)
        .join('')}`;
    return box[0];
  };
  f.oninput = sum;
  const sel = $('.picks input:checked').closest('.pick'); // mostra a sala selecionada na lista
  $('.picks').scrollTop = sel.offsetTop - 6;
  sum();
  f.elements.cpf.oninput = () => {
    f.elements.cpf.value = f.elements.cpf.value
      .replace(/\D/g, '')
      .slice(0, 11)
      .replace(
        /(\d{3})(\d{3})?(\d{3})?(\d{2})?/,
        (m, a, b, c, d) => a + (b ? '.' + b : '') + (c ? '.' + c : '') + (d ? '-' + d : ''),
      );
  };
  f.onsubmit = e => {
    e.preventDefault();
    const d = data(),
      er = $('#err'),
      bad = m => {
        er.textContent = m;
        er.hidden = false;
        er.scrollIntoView({block: 'center'});
      };
    if (d.cpf.replace(/\D/g, '').length !== 11) return bad('Informe um CPF com 11 dígitos.');
    if (DB.reqs.some(r => r.cpf && r.cpf === d.cpf && r.email !== d.email))
      return bad('Este CPF já está vinculado a outro cadastro (RN02).');
    if (sum() === 'err') return bad('Corrija os avisos de horário/capacidade em “Sua reserva”.');
    const r = {
      ...d,
      id: DB.next++,
      pax: +d.pax,
      status: 'Em análise',
      by: null,
      created: now(),
      decided: null,
    };
    delete r.ok;
    DB.reqs.push(r);
    log('Solicitação criada', r.id, `${sp(r.space).nome} em ${fd(r.date)} ${r.start}–${r.end}`);
    if (DB.role === 'emp')
      DB.guest = {
        who: r.who,
        email: r.email,
        empresa: r.empresa,
        cpf: r.cpf,
        telefone: r.telefone,
        tipo: r.tipo,
        origem: r.origem,
      };
    DB.flash = r.id;
    save();
    location.href = 'solicitacoes.html';
  };
}
/* ===== SOLICITAÇÕES ===== */
function tl(r) {
  const dec = !!r.decided,
    ok = r.status === 'Aprovada',
    bad = r.status === 'Recusada' || r.status === 'Cancelada';
  const st = (c, t, s) =>
    `<div><span class="dotc ${c}">${c === 'done' ? '✓' : c === 'bad' ? '✕' : '·'}</span><span><b>${t}</b><small>${s}</small></span></div>`;
  return `<div class="tl">${st('done', 'Solicitação enviada', dh(r.created))}${st(dec || r.status === 'Cancelada' ? 'done' : '', 'Em análise pela equipe', dec ? `${r.by} · ${dh(r.decided)}` : r.status === 'Cancelada' ? 'Encerrada' : 'Aguardando confirmação')}${ok ? st('done', 'Publicada na agenda', 'Horário reservado para você') : bad ? st('bad', r.status, esc(r.refuse || 'Registrado no histórico')) : st('', 'Confirmação no calendário', 'Somente após aprovação')}</div>`;
}
function vSol() {
  const mine = DB.reqs.filter(r => DB.role === 'eq' || r.email === ME.email).sort((a, b) => b.id - a.id),
    c = s => mine.filter(r => r.status === s).length,
    bad = r => ['Recusada', 'Cancelada'].includes(r.status);
  const rows = mine.filter(
    r =>
      (sol.tab === 'all' || (sol.tab === 'Recusada' ? bad(r) : r.status === sol.tab)) &&
      `${code(r.id)} ${sp(r.space).nome}`.toLowerCase().includes(sol.q.toLowerCase()),
  );
  const cur = mine.find(r => r.id === sol.sel) || mine[0],
    fl = DB.flash != null ? mine.find(r => r.id === DB.flash) : null,
    n0 = nt();
  if (DB.flash != null || n0) {
    DB.flash = null;
    if (n0) n0.notify = false;
    save();
  }
  app.innerHTML = `<section class="head"><div><h1>Minhas solicitações</h1><p>Acompanhe seus pedidos e veja quando sua reserva estiver confirmada.</p></div><button class="btn-primary" data-go="reserva">+ Nova solicitação</button></section>
 ${n0 ? `<div class="note ok"><b>Sua reserva está confirmada. Nos vemos no Hub!</b>${esc(sp(n0.space).nome)} reservada em seu nome em ${fd(n0.date)}, das ${n0.start} às ${n0.end}.</div>` : ''}${fl ? `<div class="note info"><b>Recebemos sua solicitação ${code(fl.id)}</b>A equipe irá analisar o pedido. O horário só será reservado após a aprovação.</div>` : ''}
 <div class="stats"><div class="stat"><b>${p2(mine.length)}</b><span>Solicitações enviadas</span></div><div class="stat"><b>${p2(c('Aprovada'))}</b><span>Reservas aprovadas</span></div><div class="stat"><b>${p2(c('Em análise'))}</b><span>Aguardando análise</span></div></div>
 <section class="card"><h2>Seus pedidos</h2><p class="sub">${DB.role === 'eq' ? 'Visão da equipe: todos os pedidos' : esc(ME.who + ' · ' + ME.email)}</p><div class="filt" style="justify-content:space-between;margin:0"><div class="tabs">${[
   ['all', 'Todas', mine.length],
   ['Em análise', 'Em análise', c('Em análise')],
   ['Aprovada', 'Aprovadas', c('Aprovada')],
   ['Recusada', 'Recusadas', mine.filter(bad).length],
 ]
   .map(
     ([k, l, n]) =>
       `<button class="tab${sol.tab === k ? ' on' : ''}" data-act="tab-sol" data-v="${k}">${l} (${n})</button>`,
   )
   .join(
     '',
   )}</div><input type="search" data-chg="solq" placeholder="Buscar por espaço ou número" value="${esc(sol.q)}"></div>
 <div class="scroll"><table class="tb"><tr><th>PEDIDO</th><th>ESPAÇO E FINALIDADE</th><th>DATA E HORÁRIO</th><th>EMPRESA</th><th>STATUS</th><th>AÇÕES</th></tr>${rows.map(r => `<tr data-act="sel-sol" data-v="${r.id}" class="${cur && cur.id === r.id ? 'sel' : ''}"><td>${code(r.id)}</td><td><b>${esc(sp(r.space).nome)}</b><small>${esc(r.reason)}</small></td><td>${fd(r.date)}<small>${r.start} – ${r.end}</small></td><td>${esc(r.empresa)}</td><td>${pill(r.status)}</td><td>${EYE}${['Em análise', 'Aprovada'].includes(r.status) ? `<button class="lk red" data-act="cancel" data-v="${r.id}">Cancelar</button>` : '<span class="lk">Detalhes</span>'}</td></tr>`).join('') || '<tr><td colspan="6" class="empty">Nenhum pedido encontrado.</td></tr>'}</table></div></section>
 ${cur ? `<section class="card" style="margin-top:14px"><h2>Acompanhamento do pedido ${code(cur.id)}</h2><p class="sub">${esc(sp(cur.space).nome)} · ${esc(cur.reason)} · ${fd(cur.date)}, ${cur.start} às ${cur.end}</p>${tl(cur)}</section>` : ''}`;
}
/* ===== APROVAÇÕES (equipe) ===== */
function vApr(p) {
  if (p && p.get('sel') && !apr.init) apr.sel = +p.get('sel');
  apr.init = 1;
  const q = DB.reqs.filter(r => r.status === 'Em análise').sort((a, b) => a.date.localeCompare(b.date)),
    cur = q.find(r => r.id === apr.sel) || q[0];
  const cf = cur ? busy(cur) : [],
    hs = cur ? DB.reqs.filter(r => r.email === cur.email && r.id !== cur.id) : [];
  app.innerHTML = `<section class="head"><div><h1>Aprovações de reservas</h1><p>Analise os pedidos, confira os horários e mantenha a agenda do Hub organizada.</p></div><button class="btn-ghost" data-go="historico">Ver histórico e auditoria</button></section>
 <div class="stats"><div class="stat"><b>${p2(q.length)}</b><span>Solicitações pendentes</span></div><div class="stat"><b>${p2(q.filter(r => prio(r) === 'Alta').length)}</b><span>Pedidos prioritários<br>Poder público / empresa contratada</span></div><div class="stat"><b>${p2(q.filter(r => busy(r).length).length)}</b><span>Conflitos de horário</span></div></div>
 <div class="note info"><b>Você está na área da equipe InPETU</b>Selecione um pedido para conferir os dados antes de aprovar ou recusar.</div>
 <div class="grid"><section class="card"><div class="filt" style="justify-content:space-between;margin:0 0 6px"><h2>Fila de solicitações</h2><span class="tz">Ordenar por data da reserva ↓</span></div><div class="scroll"><table class="tb"><tr><th>PEDIDO / SOLICITANTE</th><th>ESPAÇO E MOTIVO</th><th>DATA E HORÁRIO</th><th>PRIORIDADE</th></tr>${q.map(r => `<tr data-act="sel-apr" data-v="${r.id}" class="${cur && cur.id === r.id ? 'sel' : ''}"><td><b>${code(r.id)} · ${esc(r.who)}</b><small>${esc(r.empresa)}</small></td><td><b>${esc(sp(r.space).nome)}</b><small>${esc(r.reason)}</small></td><td>${fd(r.date)}<small>${r.start} – ${r.end}</small></td><td><span class="pill ${prio(r) === 'Alta' ? 'p-wait' : 'p-ok'}">${prio(r)}</span></td></tr>`).join('') || '<tr><td colspan="4" class="empty">Nenhuma solicitação pendente.</td></tr>'}</table></div></section>
 <aside class="card side">${
   cur
     ? `<h3 style="margin-top:0">Solicitação ${code(cur.id)}</h3><p class="count">Enviada em ${dh(cur.created)}</p>${ph(sp(cur.space))}<h3>${esc(sp(cur.space).nome)}</h3><p class="when">${fd(cur.date)} · ${cur.start} – ${cur.end} · ${cur.pax} pessoas</p>
 <div class="f"><small>SOLICITANTE</small><span>${esc(cur.who)} · ${esc(cur.tipo)}</span><br><small>${esc(cur.email)} · ${esc(cur.telefone || '—')}</small></div><div class="f"><small>HISTÓRICO DO EMPREENDEDOR</small><span>${hs.length} pedido(s) anteriores · ${hs.filter(r => r.status === 'Aprovada').length} aprovado(s)</span> <button class="lk" data-go="historico?q=${encodeURIComponent(cur.email)}">Ver histórico completo</button></div><div class="f"><small>MOTIVO E FINALIDADE</small><span>${esc(cur.reason)}</span><br><small>${esc(cur.purpose)} · Origem: ${esc(cur.origem)}</small></div>
 <div class="note ${cf.length ? 'err' : 'ok'}">${cf.length ? `Conflito com reserva aprovada: ${esc(cf[0].who)} (${cf[0].start}–${cf[0].end})` : 'Sem conflito de horário'}</div>${apr.err ? `<p class="note err">${apr.err}</p>` : ''}
 <div class="f"><small>MOTIVO DA RECUSA (OBRIGATÓRIO AO RECUSAR)</small><textarea id="motivo" rows="2" placeholder="Descreva o motivo para o solicitante"></textarea></div>
 <div class="actions"><button class="btn-danger" data-act="refuse" data-v="${cur.id}">Recusar</button><button class="btn-ok" data-act="approve" data-v="${cur.id}">Aprovar reserva</button></div>`
     : '<p class="empty">Selecione um pedido.</p>'
 }</aside></div>
 <section class="card" style="margin-top:14px"><h2>O que acontece após a decisão?</h2><div class="g2" style="margin:8px 0 0"><p><b style="color:var(--green)">Ao aprovar</b><br><small class="tz">O horário é reservado e aparece na agenda e nas solicitações do empreendedor.</small></p><p><b style="color:#b3261e">Ao recusar</b><br><small class="tz">Informe o motivo. O horário permanece disponível. Tudo fica registrado na auditoria.</small></p></div></section>`;
}
/* ===== HISTÓRICO E AUDITORIA (equipe) ===== */
const hisList = () =>
  DB.reqs
    .filter(
      r =>
        (!his.from || r.date >= his.from) &&
        (!his.to || r.date <= his.to) &&
        (!his.space || r.space === his.space) &&
        (!his.status || r.status === his.status) &&
        [code(r.id), r.who, r.empresa, r.reason, r.purpose, sp(r.space).nome]
          .join(' ')
          .toLowerCase()
          .includes(his.q.toLowerCase()),
    )
    .sort((a, b) => b.date.localeCompare(a.date) || b.start.localeCompare(a.start));
const logList = () =>
  DB.log.filter(
    l =>
      (!his.from || l.ts.slice(0, 10) >= his.from) &&
      (!his.to || l.ts.slice(0, 10) <= his.to) &&
      [l.user, l.action, code(l.id), l.detail].join(' ').toLowerCase().includes(his.q.toLowerCase()),
  );
function bars(t, rows) {
  const m = Math.max(1, ...rows.map(r => r[1]));
  return `<div class="bars"><h4>${t}</h4>${rows.map(([k, v]) => `<div class="bar"><span>${esc(k)}</span><i style="width:${(v / m) * 100}%"></i><b>${Math.round(v * 10) / 10}</b></div>`).join('') || '<p class="empty">Sem dados.</p>'}</div>`;
}
const grp = (L, fn, val = () => 1) =>
  Object.entries(L.reduce((a, r) => ((a[fn(r)] = (a[fn(r)] || 0) + val(r)), a), {})).sort(
    (a, b) => b[1] - a[1],
  );
function vHis(p) {
  if (p && p.get('q') && !his.init) his.q = p.get('q');
  his.init = 1;
  const L = hisList(),
    G = logList(),
    ap = L.filter(r => r.status === 'Aprovada'),
    dec = L.filter(r => r.decided),
    ar = L.filter(r => ['Aprovada', 'Recusada'].includes(r.status) && r.decided);
  const tm = dec.length
    ? dec.reduce((s, r) => s + (new Date(r.decided) - new Date(r.created)) / 36e5, 0) / dec.length
    : 0;
  const stat = (b, s) => `<div class="stat"><b>${b}</b><span>${s}</span></div>`;
  app.innerHTML = `<section class="head"><div><h1>Histórico e auditoria</h1><p>Métricas de uso dos espaços, histórico de reservas e registro de ações da equipe.</p></div><div class="actions" style="margin:0"><button class="btn-primary" data-act="csv-h">⬇ Baixar histórico (.csv)</button><button class="btn-ghost" data-act="csv-l">⬇ Baixar auditoria (.csv)</button></div></section>
 <div class="card filt"><label>De<input type="date" data-chg="hf" value="${his.from}"></label><label>Até<input type="date" data-chg="ht" value="${his.to}"></label><label>Espaço<select data-chg="hs"><option value="">Todos</option>${SP.map(s => `<option value="${s.id}"${his.space === s.id ? ' selected' : ''}>${s.nome}</option>`).join('')}</select></label>
 <label>Status<select data-chg="hst"><option value="">Todos</option>${['Em análise', 'Aprovada', 'Recusada', 'Cancelada'].map(s => `<option${his.status === s ? ' selected' : ''}>${s}</option>`).join('')}</select></label><label>Busca<input type="search" data-chg="hq" placeholder="Nome, empresa, finalidade" value="${esc(his.q)}"></label></div>
 <div class="stats">${stat(L.length, 'Solicitações no período')}${stat(ap.length, 'Reservas aprovadas')}${stat(ar.length ? Math.round((ap.length / ar.length) * 100) + '%' : '—', 'Taxa de aprovação')}${stat(Math.round(ap.reduce((s, r) => s + hrs(r), 0) * 10) / 10 + ' h', 'Horas reservadas (aprovadas)')}${stat(tm ? Math.round(tm * 10) / 10 + ' h' : '—', 'Tempo médio de decisão')}${stat(L.filter(r => r.status === 'Cancelada').length, 'Cancelamentos')}</div>
 <div class="g2">${bars(
   'Horas aprovadas por espaço',
   grp(ap, r => sp(r.space).nome, hrs),
 )}${bars(
   'Motivo da visita',
   grp(L, r => r.reason),
 )}${bars(
   'Origem (canal)',
   grp(L, r => r.origem),
 )}${bars(
   'Por status',
   grp(L, r => r.status),
 )}</div>
 <section class="card"><div class="tabs"><button class="tab${his.tab === 'hist' ? ' on' : ''}" data-act="htab" data-v="hist">Histórico de reservas (${L.length})</button><button class="tab${his.tab === 'log' ? ' on' : ''}" data-act="htab" data-v="log">Auditoria · log de ações (${G.length})</button></div><div class="scroll">
 ${
   his.tab === 'hist'
     ? `<table class="tb"><tr><th>PEDIDO</th><th>DATA</th><th>HORÁRIO</th><th>ESPAÇO</th><th>SOLICITANTE</th><th>MOTIVO / FINALIDADE</th><th>ORIGEM</th><th>PESSOAS</th><th>STATUS</th><th>DECIDIDO POR</th></tr>${L.map(r => `<tr><td>${code(r.id)}</td><td>${fd(r.date)}</td><td>${r.start}–${r.end}</td><td>${esc(sp(r.space).nome)}</td><td>${esc(r.who)}<small>${esc(r.empresa)}</small></td><td>${esc(r.reason)}<small>${esc(r.purpose)}</small></td><td>${esc(r.origem)}</td><td>${r.pax}</td><td>${pill(r.status)}</td><td>${esc(r.by || '—')}</td></tr>`).join('') || '<tr><td colspan="10" class="empty">Nada encontrado.</td></tr>'}</table>`
     : `<table class="tb"><tr><th>DATA E HORA</th><th>USUÁRIO</th><th>AÇÃO</th><th>PEDIDO</th><th>DETALHE</th></tr>${G.map(l => `<tr><td>${dh(l.ts)}</td><td>${esc(l.user)}</td><td>${esc(l.action)}</td><td>${l.id === '' || l.id == null ? '—' : code(l.id)}</td><td>${esc(l.detail)}</td></tr>`).join('') || '<tr><td colspan="5" class="empty">Nada encontrado.</td></tr>'}</table>`
 }</div></section>`;
}
function csv(name, head, rows) {
  const q = v => `"${String(v ?? '').replace(/"/g, '""')}"`,
    a = document.createElement('a');
  a.href = URL.createObjectURL(
    new Blob(['\ufeff' + [head, ...rows].map(r => r.map(q).join(';')).join('\r\n')], {
      type: 'text/csv;charset=utf-8',
    }),
  );
  a.download = `${name}-${iso(new Date())}.csv`;
  a.click();
  URL.revokeObjectURL(a.href);
}
/* ===== AÇÕES ===== */
const decide = (id, st, extra) => {
  const r = DB.reqs.find(x => x.id == id);
  Object.assign(r, {status: st, by: ME.who, decided: now(), notify: st === 'Aprovada'}, extra);
  log(
    st === 'Aprovada' ? 'Reserva aprovada' : 'Reserva recusada',
    r.id,
    extra?.refuse || `${sp(r.space).nome} ${fd(r.date)} ${r.start}–${r.end}`,
  );
  save();
  apr.err = '';
};
const ACT = {
  mode(v) {
    if (v === 'eq') {
      if (DB.role !== 'eq') askLogin();
      return;
    }
    if (DB.role === 'eq') {
      log('Logout da equipe', '');
      DB.role = 'emp';
      DB.staff = null;
      ME = DB.guest;
      save();
      if (['aprovacoes', 'historico'].includes(document.body.dataset.page))
        return (location.href = 'agenda.html');
    }
    route();
  },
  async reset() {
    if (confirm('Restaurar os dados de exemplo? Cadastros e reservas criados serão apagados.')) {
      DB = await seed();
      save();
      location.href = 'agenda.html';
    }
  },
  day(v) {
    cal.s = v;
    route();
  },
  prev() {
    cal.v = new Date(cal.v.getFullYear(), cal.v.getMonth() - 1, 1);
    route();
  },
  next() {
    cal.v = new Date(cal.v.getFullYear(), cal.v.getMonth() + 1, 1);
    route();
  },
  today() {
    const t = new Date();
    cal.s = iso(t);
    cal.v = new Date(t.getFullYear(), t.getMonth(), 1);
    route();
  },
  'tab-sol'(v) {
    sol.tab = v;
    route();
  },
  'sel-sol'(v) {
    sol.sel = +v;
    route();
  },
  cancel(v, _, e) {
    e.stopPropagation();
    const r = DB.reqs.find(x => x.id == v);
    if (!confirm(`Cancelar a solicitação ${code(r.id)}?`)) return;
    r.status = 'Cancelada';
    log('Reserva cancelada', r.id, `${sp(r.space).nome} ${fd(r.date)}`);
    save();
    route();
  },
  'sel-esp'(v) {
    esp.sel = v;
    route();
  },
  scroll(v) {
    $('.track').scrollBy({left: v * 270, behavior: 'smooth'});
  },
  'sel-apr'(v) {
    apr.sel = +v;
    apr.err = '';
    route();
  },
  approve(v) {
    const r = DB.reqs.find(x => x.id == v);
    if (busy(r).length) {
      apr.err = 'Não é possível aprovar: o espaço já tem reserva aprovada nesse horário (RN04).';
      return route();
    }
    decide(v, 'Aprovada');
    route();
  },
  refuse(v) {
    const m = $('#motivo').value.trim();
    if (!m) {
      apr.err = 'Informe o motivo da recusa.';
      return route();
    }
    decide(v, 'Recusada', {refuse: m});
    route();
  },
  htab(v) {
    his.tab = v;
    route();
  },
  'csv-h'() {
    const L = hisList();
    log('Exportação CSV', '', 'Histórico de reservas · ' + L.length + ' linhas');
    save();
    csv(
      'historico-reservas-inpetu',
      [
        'Pedido',
        'Data',
        'Início',
        'Fim',
        'Espaço',
        'Solicitante',
        'E-mail',
        'Empresa',
        'Tipo',
        'Pessoas',
        'Motivo',
        'Finalidade',
        'Origem',
        'Status',
        'Decidido por',
        'Criado em',
        'Decidido em',
        'Motivo da recusa',
      ],
      L.map(r => [
        code(r.id),
        fd(r.date),
        r.start,
        r.end,
        sp(r.space).nome,
        r.who,
        r.email,
        r.empresa,
        r.tipo,
        r.pax,
        r.reason,
        r.purpose,
        r.origem,
        r.status,
        r.by || '',
        dh(r.created),
        dh(r.decided),
        r.refuse || '',
      ]),
    );
    route();
  },
  'csv-l'() {
    const G = logList();
    log('Exportação CSV', '', 'Log de auditoria · ' + G.length + ' linhas');
    save();
    csv(
      'auditoria-inpetu',
      ['Data e hora', 'Usuário', 'Ação', 'Pedido', 'Detalhe'],
      G.map(l => [dh(l.ts), l.user, l.action, l.id === '' || l.id == null ? '' : code(l.id), l.detail]),
    );
    route();
  },
};
const CHG = {
  calf: v => (cal.f = v),
  ed: v => (esp.d = v),
  es: v => (esp.s = v),
  ee: v => (esp.e = v),
  solq: v => (sol.q = v),
  hf: v => (his.from = v),
  ht: v => (his.to = v),
  hs: v => (his.space = v),
  hst: v => (his.status = v),
  hq: v => (his.q = v),
};
document.addEventListener('click', e => {
  const t = e.target.closest('[data-go],[data-act]');
  if (!t) return;
  if (t.dataset.go) {
    const [g, q] = t.dataset.go.split('?');
    location.href = g + '.html' + (q ? '?' + q : '');
    return;
  }
  ACT[t.dataset.act]?.(t.dataset.v, t, e);
});
document.addEventListener('input', e => {
  const t = e.target,
    k = t.dataset.chg;
  if (!k) return;
  CHG[k](t.value);
  route();
  if (t.type === 'search') {
    const n = $(`[data-chg="${k}"]`);
    n.focus();
    n.setSelectionRange(n.value.length, n.value.length);
  }
});
/* ===== ROTAS ===== */
const NAV = {
  emp: [
    ['agenda', 'Agenda'],
    ['solicitacoes', 'Minhas solicitações'],
    ['espacos', 'Espaços'],
  ],
  eq: [
    ['agenda', 'Agenda'],
    ['solicitacoes', 'Minhas solicitações'],
    ['espacos', 'Espaços'],
    ['aprovacoes', 'Aprovações'],
    ['historico', 'Histórico'],
  ],
};
function route() {
  const n = document.body.dataset.page,
    q = location.search;
  if (['aprovacoes', 'historico'].includes(n) && DB.role !== 'eq') {
    location.replace('agenda.html');
    return;
  }
  const V = {
    agenda: vAgenda,
    solicitacoes: vSol,
    espacos: vEsp,
    reserva: vRes,
    aprovacoes: vApr,
    historico: vHis,
  };
  (V[n] || vAgenda)(new URLSearchParams(q || ''));
  const cur = n === 'reserva' ? 'solicitacoes' : n;
  $('#nav').innerHTML = NAV[DB.role]
    .map(([k, l]) => `<a href="${k}.html" class="${k === cur ? 'is-active' : ''}">${l}</a>`)
    .join('');
  document.querySelectorAll('#seg button').forEach(b => b.classList.toggle('is-on', b.dataset.v === DB.role));
  $('#fh').textContent =
    {
      agenda: 'Selecione um dia para consultar o responsável, a empresa, o motivo e os horários reservados.',
      solicitacoes: 'Precisa de ajuda? Fale com a equipe do InPETU Hub.',
      aprovacoes: 'As decisões ficam registradas no histórico, com data e responsável pela análise.',
    }[n] || '';
}

/* ===== LOGIN DA EQUIPE (modal) ===== */
function askLogin() {
  const d = document.createElement('dialog');
  d.className = 'dlg';
  d.innerHTML = `<form id="sf"><h2>Acesso da equipe InPETU</h2><p class="sub">Entre com o e-mail e a senha da equipe para ver aprovações, histórico e auditoria.</p><div class="fg" style="grid-template-columns:1fr"><label>E-mail<input name="email" type="email" required autofocus></label><label>Senha<input name="senha" type="password" required></label></div><p id="serr" class="note err" hidden></p><div class="actions"><button type="button" class="btn-ghost" id="sc">Cancelar</button><button class="btn-primary">Entrar</button></div></form>`;
  document.body.append(d);
  d.showModal();
  $('#sc').onclick = () => d.remove();
  d.addEventListener('cancel', () => d.remove());
  $('#sf').onsubmit = async e => {
    e.preventDefault();
    const f = Object.fromEntries(new FormData(e.target)),
      u = DB.users.find(x => x.email.toLowerCase() === f.email.trim().toLowerCase());
    if (!u || u.pass !== (await hash(f.senha))) {
      const n = $('#serr');
      n.textContent = 'E-mail ou senha incorretos.';
      n.hidden = false;
      return;
    }
    DB.staff = u.id;
    DB.role = 'eq';
    ME = u;
    log('Login da equipe', '');
    save();
    d.remove();
    route();
  };
}
/* ===== INÍCIO ===== */
(async () => {
  if (!DB) DB = await seed();
  const st = DB.users.find(u => u.id === DB.staff);
  if (DB.role === 'eq' && !st) DB.role = 'emp';
  ME = DB.role === 'eq' ? st : DB.guest;
  if (['aprovacoes', 'historico'].includes(document.body.dataset.page) && DB.role !== 'eq')
    return location.replace('agenda.html');
  route();
})();
